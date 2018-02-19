package hmda.http.model.directives

import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import hmda.http.model.directives.AroundDirectives.aroundRequest
import io.prometheus.client.{Gauge, Histogram}

import scala.concurrent.ExecutionContext
import scala.util.{Success, Try}

object ApiMetrics {

  def requestStats(requestsInProgress: Gauge, requestLatency: Histogram)(
      route: Route)(implicit ec: ExecutionContext) =
    aroundRequest(reportRequestMetrics(requestsInProgress, requestLatency))(ec)(
      route)

  private def reportRequestMetrics(requestsInProgress: Gauge,
                                   requestLatency: Histogram)(
      ctx: RequestContext): Try[RouteResult] => Unit = {
    requestsInProgress.inc()
    val requestTimer = requestLatency.startTimer()

    result =>
      requestsInProgress.dec()
      result match {
        case Success(Complete(_)) => requestTimer.observeDuration()
        case _                    => // do nothing
      }
  }

}
