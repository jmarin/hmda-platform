package hmda.api.http.public

import akka.actor.{ActorRef, ActorSystem}
import akka.event.LoggingAdapter
import akka.stream.ActorMaterializer
import akka.util.Timeout
import hmda.api.http.HmdaCustomDirectives
import hmda.api.protocol.fi.ts.TsProtocol
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{StatusCodes, Uri}
import hmda.api.protocol.validation.ValidationResultProtocol
import hmda.model.fi.ts.TransmittalSheet
import hmda.parser.fi.ts.TsCsvParser
import hmda.persistence.model.HmdaSupervisorActor.FindActorByName

import scala.concurrent.ExecutionContext

trait SingleTsValidationHttpApi extends TsProtocol with ValidationResultProtocol with HmdaCustomDirectives {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  val log: LoggingAdapter
  implicit val ec: ExecutionContext
  implicit val timeout: Timeout

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

  def validateTsRoute(supervisor: ActorRef) =
    path("validate") {
      parameters('check.as[String] ? "all") { checkType =>
        timedPost { uri =>
          entity(as[TransmittalSheet]) { ts =>
            validateRoute(supervisor, ts, checkType, uri)
          }
        }
      }
    }

  def parseAndValidateTsRoute(supervisor: ActorRef) =
    path("parseAndValidate") {
      parameters('check.as[String] ? "all") { checkType =>
        timedPost{ uri =>
          entity(as[String]) { s =>
            TsCsvParser(s) match {
              case Right(ts) => validateRoute(supervisor, ts, checkType, uri)
              case Left(errors) => complete(ToResponseMarshallable(StatusCodes.BadRequest -> errors))
            }
          }
        }
      }
    }

  def validateRoute(supervisor: ActorRef, ts: TransmittalSheet, checkType: String, uri: Uri) = {
    ???
    //val fTsValidation = (supervisor ? FindActorByName(SingleTsValidation.name)).mapTo[ActorRef]
  }

  def tsRoutes(supervisor: ActorRef) =
    encodeResponse {
      pathPrefix("ts") {
        parseLarRoute ~ validateTsRoute(supervisor) ~ parseAndValidateTsRoute(supervisor)
      }
    }

}
