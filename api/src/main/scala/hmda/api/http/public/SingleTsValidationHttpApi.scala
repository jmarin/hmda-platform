package hmda.api.http.public

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import akka.util.Timeout
import hmda.api.http.HmdaCustomDirectives
import hmda.api.protocol.fi.ts.TsProtocol
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{ StatusCodes, Uri }
import hmda.api.protocol.validation.ValidationResultProtocol
import hmda.model.fi.ts.TransmittalSheet
import hmda.parser.fi.ts.TsCsvParser

import scala.concurrent.ExecutionContext

trait SingleTsValidationHttpApi extends TsProtocol with ValidationResultProtocol with HmdaCustomDirectives {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  val log: LoggingAdapter
  implicit val ec: ExecutionContext
  implicit val timeout: Timeout

  // ts/parse
  val parseLarRoute =
    path("parse") {
      timedPost { _ =>
        entity(as[String]) { s =>
          TsCsvParser(s) match {
            case Right(ts) => complete(ToResponseMarshallable(ts))
            case Left(errors) => complete(ToResponseMarshallable(StatusCodes.BadRequest -> errors))
          }
        }
      }
    }

  def validateTsRoute(supervisor: ActorRef) = ???

  def parseAndValidateTsRoute(supervisor: ActorRef) = ???

  def validateRoute(supervisor: ActorRef, ts: TransmittalSheet, checkType: String, uri: Uri) = ???

  def tsRoutes(supervisor: ActorRef) =
    encodeResponse {
      pathPrefix("ts") {
        parseLarRoute
      }
    }

}
