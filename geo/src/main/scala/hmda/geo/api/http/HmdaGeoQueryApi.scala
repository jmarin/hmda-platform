package hmda.geo.api.http

import akka.actor.{ActorSystem, Props}
import akka.pattern.pipe
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import hmda.api.http.HttpServer
import hmda.api.http.routes.BaseHttpApi
import akka.http.scaladsl.server.Directives._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

object HmdaGeoQueryApi {
  def props(): Props = Props(new HmdaGeoQueryApi)
}

class HmdaGeoQueryApi
    extends HttpServer
    with BaseHttpApi
    with HmdaGeoQueryHttpApi {

  val config = ConfigFactory.load()

  override implicit val system: ActorSystem = context.system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override implicit val ec: ExecutionContext = context.dispatcher
  override val log = Logging(system, getClass)

  val duration = config.getInt("hmda.geo.http.timeout").seconds

  override implicit val timeout: Timeout = Timeout(duration)

  val createSchema = config.getBoolean("hmda.geo.createSchema")

  if (createSchema) {}

  override val name: String = "hmda-geo-api"
  override val host: String = config.getString("hmda.geo.http.host")
  override val port: Int = config.getInt("hmda.geo.http.port")

  override val paths: Route = routes(s"$name") ~ censusPaths

  override val http: Future[Http.ServerBinding] = Http(system).bindAndHandle(
    paths,
    host,
    port
  )

  http pipeTo self

}
