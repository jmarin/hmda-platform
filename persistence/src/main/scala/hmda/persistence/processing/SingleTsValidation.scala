package hmda.persistence.processing

import akka.actor.{Actor, ActorLogging, Props}
import hmda.model.fi.ts.TransmittalSheet
import hmda.validation.context.ValidationContext
import hmda.validation.engine.ts.TsEngine

import scala.concurrent.{ExecutionContext, Future}

object SingleTsValidation {

  val name = "tsValidation"

  def props: Props = Props(new SingleTsValidation)

  case class CheckAll(ts: TransmittalSheet, ctx: ValidationContext)
  case class CheckSyntactical(ts: TransmittalSheet, ctx: ValidationContext))
  case class CheckValidity(ts: TransmittalSheet, ctx: ValidationContext))
  case class CheckQuality(ts: TransmittalSheet, ctx: ValidationContext))
}

class SingleTsValidation extends Actor with ActorLogging with TsEngine {
  import SingleTsValidation._

  override implicit val ec: ExecutionContext = context.dispatcher

  override def receive: Receive = {

    case CheckAll(ts, ctx) =>
      log.debug(s"Checking all edits on TS: ${ts.toCSV}")
      val f1 = Future(validationErrors(ts, ctx, checkSyntactical))
      val f2 = checkSyntacticalAsync(ts, ctx)

      //sender() ! validationErrors(ts, ctx, validateTs)
    case CheckSyntactical(ts, ctx) =>
      log.debug(s"Checking syntactical on TS: ${ts.toCSV}")
      sender() ! validationErrors(ts, ctx, checkSyntactical)
    case CheckValidity(ts, ctx) =>
      log.debug(s"Checking validity on TS: ${ts.toCSV}")
      sender() ! validationErrors(ts, ctx, checkValidity)
    case CheckQuality(ts, ctx) =>
      log.debug(s"Checking quality on TS: ${ts.toCSV}")
      sender() ! validationErrors(ts, ctx, checkQuality)

    case _ => log.error(s"Unsupported message sent to ${self.path}")
  }


}
