package hmda.http.model.directives

import akka.http.scaladsl.server.Directives.{
  extractRequestContext,
  mapRouteResult
}
import akka.http.scaladsl.server.{Directive0, RequestContext, RouteResult}
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import akka.stream.scaladsl.Flow
import akka.util.ByteString

import scala.concurrent.ExecutionContext
import scala.util.{Success, Try}

object AroundDirectives {

  /**
    * @param onRequest Called when a request starts; the result (a function
    *                  `Try[RouteResult => Unit]`) is applied to when the
    *                  request completes either successfully or fails.
    */
  def aroundRequest[T](onRequest: RequestContext => Try[RouteResult] => Unit)(
      implicit ec: ExecutionContext): Directive0 = {

    extractRequestContext.flatMap { ctx =>
      val onDone = onRequest(ctx)
      mapRouteResult {
        case c @ Complete(response) =>
          Complete(response.mapEntity { entity =>
            entity.transformDataBytes(Flow[ByteString].watchTermination() {
              case (m, f) =>
                f.map(_ => c).onComplete(onDone)
                m
            })
          })
        case r: Rejected =>
          onDone(Success(r))
          r
      }
    }
  }

}
