package hmda.http.api

import java.net.InetAddress
import java.time.Instant

import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import hmda.http.model.common.HmdaServiceStatus
import hmda.http.model.directives.HmdaTimeDirectives
import io.circe.generic.auto._
import hmda.http.model.directives.ApiMetrics._
import scala.concurrent.ExecutionContext
import hmda.http.model.BaseApiMetricsCollectors._

trait BaseHttpApi extends HmdaTimeDirectives {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  val log: LoggingAdapter

  def rootPath(name: String) =
    pathSingleSlash {
      complete {
        val now = Instant.now.toString
        val host = InetAddress.getLocalHost.getHostName
        val status = HmdaServiceStatus("OK", name, now, host)
        log.debug(status.toString)
        ToResponseMarshallable(status)
      }
    }

  def routes(apiName: String)(implicit ec: ExecutionContext) = encodeResponse {

    val requestsInProgress = apiName match {
      case "hmda-filing-api"  => filingApiRequestsInProgress
      case "hmda-admin-api"   => adminApiRequestsInProgress
      case "hmda-public-api"  => publicApiRequestsInProgress
      case "hmda-jvm-metrics" => jvmMetricsApiRequestsInProgress
    }

    val requestLatency = apiName match {
      case "hmda-filing-api"  => filingApiRequestLatency
      case "hmda-admin-api"   => adminApiRequestLatency
      case "hmda-public-api"  => publicApiRequestLatency
      case "hmda-jvm-metrics" => jvmMetricsApiRequestLatency
    }

    requestStats(requestsInProgress, requestLatency) {
      rootPath(apiName)
    }
  }
}
