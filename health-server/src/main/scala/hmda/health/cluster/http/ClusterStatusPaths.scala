package hmda.health.cluster.http

import akka.NotUsed
import akka.actor.{ActorContext, ActorRef, ActorSystem}
import akka.http.scaladsl.model.ws.{BinaryMessage, Message, TextMessage}
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import hmda.health.cluster.ClusterStatus

trait ClusterStatusPaths {

  implicit val materializer: ActorMaterializer

  def clusterStatusFlow: Flow[Message, Message, NotUsed] =
    Flow[Message].mapConcat {
      case tm: TextMessage =>
        TextMessage(
          Source.single("Hello ") ++ tm.textStream ++ Source.single("!")) :: Nil
      case bm: BinaryMessage =>
        // ignore binary messages but drain content to avoid the stream being clogged
        bm.dataStream.runWith(Sink.ignore)
        Nil
    }

  def clusterStatusPath(context: ActorContext) =
    pathPrefix("cluster") {
      path("status") {
        val clusterStatus = context.actorOf(ClusterStatus.props)
        handleWebSocketMessages(clusterStatusFlow)
      }
    }

  def clusterStatusRoutes(context: ActorContext) = clusterStatusPath(context)

}
