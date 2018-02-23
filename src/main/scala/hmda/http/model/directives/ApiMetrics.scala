package hmda.http.model.directives

import akka.http.scaladsl.server.RouteResult.Complete
import akka.http.scaladsl.server.{RequestContext, Route, RouteResult}
import hmda.http.model.directives.AroundDirectives.aroundRequest
import io.prometheus.client.Histogram

import scala.concurrent.ExecutionContext
import scala.util.{Success, Try}

object ApiMetrics {

  def requestStats(serviceName: String, requestLatency: Histogram)(
      route: Route)(implicit ec: ExecutionContext) = {

    val initialTime = System.currentTimeMillis()
    aroundRequest(
      reportRequestMetrics(initialTime, serviceName, requestLatency))(ec)(route)
  }

  private def reportRequestMetrics(initialTime: Long,
                                   serviceName: String,
                                   requestLatency: Histogram)(
      ctx: RequestContext): Try[RouteResult] => Unit = { result =>
    result match {
      case Success(Complete(response)) =>
        val duration = System.currentTimeMillis() - initialTime
        requestLatency
          .labels(serviceName,
                  ctx.request.method.value,
                  response.status.intValue().toString)
          .observe(duration.toDouble)
      case _ => // do nothing
    }
  }

}
