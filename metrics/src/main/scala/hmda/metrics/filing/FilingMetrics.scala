package hmda.metrics.filing

import akka.actor.{ ActorPath, ActorRef, ActorSystem }
import akka.pattern.ask
import akka.cluster.client.{ ClusterClient, ClusterClientSettings }
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import hmda.model.metrics.FilingSummaryStats
import hmda.persistence.HmdaSupervisor.FindHmdaFiling
import hmda.persistence.messages.CommonMessages.GetState
import hmda.persistence.messages.commands.filing.FilingCommands.GetFilingSummaryStats
import hmda.persistence.processing.HmdaFiling.HmdaFilingState
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class FilingMetrics {

  implicit val system: ActorSystem = ActorSystem("hmda-metrics-client")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  val config = ConfigFactory.load()

  val log = LoggerFactory.getLogger("hmda-lar-loader")

  val hmdaClusterName = config.getString("hmda.cluster.name")
  val hmdaClusterIP = config.getString("hmda.cluster.host")
  val hmdaClusterPort = config.getInt("hmda.cluster.port")
  val actorTimeout = config.getInt("hmda.actorTimeout")

  implicit val timeout = Timeout(actorTimeout.seconds)

  val initialContacts = Set(
    ActorPath.fromString(s"akka.tcp://$hmdaClusterName@$hmdaClusterIP:$hmdaClusterPort/system/receptionist")
  )
  val settings = ClusterClientSettings(system)
    .withInitialContacts(initialContacts)

  val clusterClient = system.actorOf(ClusterClient.props(settings), "hmda-filing-metrics-client")

  def retrieveFilingStats(period: String): Unit = {
    val fHmdaFilng = (clusterClient ? ClusterClient
      .Send(
        "/user/supervisor/singleton",
        FindHmdaFiling(period),
        localAffinity = true
      )).mapTo[ActorRef]

    val fFilingState = for {
      hmdaFiling <- fHmdaFilng
      filingSummaryStats <- (hmdaFiling ? GetFilingSummaryStats).mapTo[FilingSummaryStats]
    } yield filingSummaryStats

    fFilingState.onComplete { summaryStats =>
      log.info(summaryStats.toString)
    }

  }

}
