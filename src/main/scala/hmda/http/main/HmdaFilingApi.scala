package hmda.http.main

import akka.actor.{ActorSystem, Props}
import akka.pattern.pipe
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import hmda.http.api.BaseHttpApi
import hmda.http.model.common.HttpServer
import hmda.http.api.ServiceNames._

import scala.concurrent.{ExecutionContext, Future}

object HmdaFilingApi {
  def props: Props = Props(new HmdaFilingApi)
}

class HmdaFilingApi extends HttpServer with BaseHttpApi {
  val config = ConfigFactory.load()

  override implicit val system: ActorSystem = context.system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override implicit val ec: ExecutionContext = context.dispatcher
  override val log = Logging(system, getClass)

  override val name: String = hmdaFilingRoot
  override val host: String = config.getString("hmda.http.filingHost")
  override val port: Int = config.getInt("hmda.http.filingPort")

  override val paths: Route = routes(s"$name")

  override val http: Future[Http.ServerBinding] = Http(system).bindAndHandle(
    paths,
    host,
    port
  )

  http pipeTo self

}
