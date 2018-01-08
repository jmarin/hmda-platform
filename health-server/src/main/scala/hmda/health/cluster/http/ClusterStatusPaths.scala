package hmda.health.cluster.http

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.Message
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, RunnableGraph, Source}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.duration._

trait ClusterStatusPaths {

  implicit val materializer: ActorMaterializer

  //val source = Source(1 to 1000).map(_.toString)
  //Source.actorRef[MemberDetails](Int.MaxValue, OverflowStrategy.dropBuffer)

  //val runnableGraph: RunnableGraph[Source[String, NotUsed]] =
  //  source.toMat(BroadcastHub.sink(bufferSize = 256))(Keep.right)

  //val producer: Source[String, NotUsed] = runnableGraph.run()
  //
  //  // Optional - add sink to avoid backpressuring the original flow when no clients are attached
  //  //  producer.runWith(Sink.ignore)
  //
  //  private val clusterStatusHandler: Flow[Message, Message, NotUsed] =
  //    Flow[Message]
  //      .mapConcat(_ => scala.collection.immutable.Seq.empty[MemberDetails])
  //      .merge(producer)
  //      .map(s => TextMessage(s.toString))

  val clusterStatusPath =
    pathPrefix("cluster") {
      path("status") {
        complete("OK")
      }
    }

  def clusterStatusRoutes(messageFlow: Flow[Message, Message, NotUsed]): Route =
    clusterStatusPath

}
