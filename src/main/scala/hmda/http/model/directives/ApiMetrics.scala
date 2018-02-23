package hmda.http.model.directives

import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import hmda.http.model.directives.AroundDirectives.aroundRequest
import io.prometheus.client.Histogram

import scala.concurrent.ExecutionContext
import scala.util.{Success, Try}

object ApiMetrics {

  def requestStats(serviceName: String, requestLatency: Histogram)(
      route: Route)(implicit ec: ExecutionContext) =
    aroundRequest(reportRequestMetrics(serviceName, requestLatency))(ec)(route)

  private def reportRequestMetrics(serviceName: String,
                                   requestLatency: Histogram)(
      ctx: RequestContext): Try[RouteResult] => Unit = {
    val requestTimer =
      requestLatency
        .labels(serviceName, ctx.request.method.value)
        .startTimer()

    result =>
      result match {
        case Success(Complete(response)) =>
          requestLatency.labels(serviceName, ctx.request.method.value)
          requestTimer.observeDuration()
        case _ => // do nothing
      }
  }

}
