package hmda.health

import akka.NotUsed
import akka.actor.{ActorSystem, Props, Terminated}
import akka.event.Logging
import akka.pattern.pipe
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import hmda.health.cluster.http.ClusterStatusPaths
import hmda.http.common.api.{BaseHttpApi, HttpServer}
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.{BroadcastHub, Flow, Keep, RunnableGraph, Source}
import hmda.model.cluster.MemberDetails

import scala.concurrent.{ExecutionContext, Future}

object HmdaHealth {
  val name = "HmdaHealth"

  def props: Props = Props(new HmdaHealth)
}

class HmdaHealth extends HttpServer with BaseHttpApi with ClusterStatusPaths {

  val config = ConfigFactory.load()

  override val name: String = "hmda-health-api"
  override val host: String = config.getString("hmda.health.http.host")
  override val port: Int = config.getInt("hmda.health.http.port")
  override implicit val system: ActorSystem = context.system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override implicit val ec: ExecutionContext = context.dispatcher
  override val log = Logging(system, getClass)

  val source = Source(1 to 1000).map(_.toString)

  val runnableGraph: RunnableGraph[Source[String, NotUsed]] =
    source.toMat(BroadcastHub.sink(bufferSize = 256))(Keep.right)

  val producer: Source[String, NotUsed] = runnableGraph.run()

  private val clusterStatusHandler: Flow[Message, Message, NotUsed] =
    Flow[Message]
      .mapConcat(_ => scala.collection.immutable.Seq.empty[MemberDetails])
      .merge(producer)
      .map(s => TextMessage(s.toString))

  override val paths: Route = routes(s"$name") ~ clusterStatusRoutes(
    clusterStatusHandler)

  override val http: Future[Http.ServerBinding] = Http(system).bindAndHandle(
    paths,
    host,
    port
  )

  http pipeTo self

  override def receive = super.receive orElse {
    case Terminated(ref) =>
      log.debug(s"$ref died")

    case _ => //
  }

}
