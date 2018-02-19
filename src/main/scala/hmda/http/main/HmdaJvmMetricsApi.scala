package hmda.http.main

import akka.actor.{ActorSystem, Props}
import akka.pattern.pipe
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import hmda.http.model.common.HttpServer
import com.codahale.metrics.MetricRegistry
import hmda.http.api.{BaseHttpApi, JvmMetricsApi}

import scala.concurrent.{ExecutionContext, Future}
import io.prometheus.client.dropwizard.DropwizardExports
import io.prometheus.client.hotspot.DefaultExports
import io.prometheus.client.CollectorRegistry
import hmda.http.api.ServiceNames._

object HmdaJvmMetricsApi {
  DefaultExports.initialize()
  val metricRegistry = new MetricRegistry()
  CollectorRegistry.defaultRegistry.register(
    new DropwizardExports(metricRegistry))

  def props(): Props = Props(new HmdaJvmMetricsApi)
}

class HmdaJvmMetricsApi extends HttpServer with BaseHttpApi with JvmMetricsApi {

  val config = ConfigFactory.load()

  override implicit val system: ActorSystem = context.system
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override implicit val ec: ExecutionContext = context.dispatcher
  override val log = Logging(system, getClass)

  override val name: String = hmdaJvmMetricsApi
  override val host: String = config.getString("hmda.http.filingHost")
  override val port: Int = config.getInt("hmda.http.jvmMetricsPort")

  override val paths: Route = routes(s"$name") ~ prometheusPath(
    CollectorRegistry.defaultRegistry)

  override val http: Future[Http.ServerBinding] = Http(system).bindAndHandle(
    paths,
    host,
    port
  )

  http pipeTo self

}
