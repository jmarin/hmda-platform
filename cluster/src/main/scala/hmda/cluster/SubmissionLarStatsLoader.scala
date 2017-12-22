package hmda.cluster

import java.io.File
import java.time.Instant

import akka.NotUsed
import akka.actor.{ ActorRef, ActorSystem }
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ FileIO, Flow, Framing, Sink }
import akka.util.{ ByteString, Timeout }
import hmda.model.fi.{ Submission, SubmissionId }
import hmda.parser.fi.lar.LarCsvParser
import hmda.parser.fi.ts.TsCsvParser
import hmda.persistence.HmdaSupervisor
import hmda.persistence.HmdaSupervisor.{ FindProcessingActor, FindSubmissions }
import hmda.persistence.institutions.SubmissionPersistence
import hmda.persistence.institutions.SubmissionPersistence.{ CreateSubmission, GetLatestSubmission }
import hmda.persistence.messages.events.processing.CommonHmdaValidatorEvents.LarValidated
import hmda.persistence.messages.events.processing.FileUploadEvents.LineAdded
import hmda.persistence.processing.SubmissionManager
import hmda.persistence.processing.SubmissionManager.GetActorRef
import hmda.validation.messages.ValidationStatsMessages.FindTotalSubmittedLars
import hmda.validation.stats.SubmissionLarStats.{ CountSubmittedLarsInSubmission, PersistIrs, PersistStatsForMacroEdits }
import hmda.validation.stats.ValidationStats.AddSubmissionTaxId
import hmda.validation.stats.{ SubmissionLarStats, ValidationStats }

import scala.concurrent.duration._

object SubmissionLarStatsLoader {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val duration = 5.seconds
  implicit val timeout = Timeout(duration)

  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      sys.exit(0)
    }

    val path = new File(args(0))
    if (!path.isDirectory) {
      sys.exit(0)
    }

    val period = args(1)

    val fileList = path.listFiles().toSet
    val fileNames = fileList.map(file => file.getName).filter(name => name.endsWith(".txt"))

    fileNames.foreach(fileName => processLars(new File(s"$path/$fileName"), s"${fileName.substring(0, fileName.indexOf("."))}", period))
  }

  def processLars(file: File, institutionId: String, period: String): Unit = {
    val validationStats = system.actorOf(ValidationStats.props())
    val supervisor = system.actorOf(HmdaSupervisor.props(validationStats))

    val fLarStats = for {
      submissions <- (supervisor ? FindSubmissions(SubmissionPersistence.name, institutionId, period)).mapTo[ActorRef]
      _ <- (submissions ? CreateSubmission).mapTo[Option[Submission]]
      latest <- (submissions ? GetLatestSubmission).mapTo[Submission].map(s => s.id.sequenceNumber)
      submissionId = SubmissionId(institutionId, period, latest)
      submissionManager <- (supervisor ? FindProcessingActor(SubmissionManager.name, submissionId)).mapTo[ActorRef]
      larStats <- (submissionManager ? GetActorRef(SubmissionLarStats.name)).mapTo[ActorRef]
    } yield (larStats, submissionId)

    fLarStats.map {
      case (larStats, submissionId) =>
        val source = FileIO.fromPath(file.toPath)
        source
          .via(framing)
          .drop(1)
          .map(s => s.utf8String)
          .map { s => larStats ! LineAdded(Instant.now.getEpochSecond, s); s }
          .map(s => LarCsvParser(s).right.get)
          .map { lar => larStats ! LarValidated(lar, submissionId); lar }
          .runWith(Sink.ignore)
          .onComplete { _ =>
            larStats ! CountSubmittedLarsInSubmission
            larStats ! PersistStatsForMacroEdits
            larStats ! PersistIrs
            (larStats ? FindTotalSubmittedLars(institutionId, period))
              .mapTo[Int]
              .map { n => println(s"submitted: $n"); n }
          }

        source
          .via(framing)
          .take(1)
          .map(s => s.utf8String)
          .map(s => TsCsvParser(s).right.get)
          .map { ts => larStats ! AddSubmissionTaxId(ts.taxId, submissionId); ts }
          .runWith(Sink.ignore)

    }

  }

  def framing: Flow[ByteString, ByteString, NotUsed] = {
    Framing.delimiter(ByteString("\n"), maximumFrameLength = 65536, allowTruncation = true)
  }

}
