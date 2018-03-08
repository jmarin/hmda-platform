package hmda.parser.filing.lar

import hmda.parser.ParserErrorModel._

object LarParserErrorModel {

  case object InvalidId extends ParserValidationError {
    override def errorMessage: String = notAnInteger("id")
  }

  case object InvalidApplicationDate extends ParserValidationError {
    override def errorMessage: String = notAnInteger("application date")
  }

  case object InvalidLoanType extends ParserValidationError {
    override def errorMessage: String = notAnInteger("loan type")
  }

  case object InvalidLoanPurpose extends ParserValidationError {
    override def errorMessage: String = notAnInteger("loan purpose")
  }

  case object InvalidPreapproval extends ParserValidationError {
    override def errorMessage: String = notAnInteger("preapproval")
  }

  case object InvalidConstructionMethod extends ParserValidationError {
    override def errorMessage: String = notAnInteger("construction method")
  }

  case object InvalidOccupancy extends ParserValidationError {
    override def errorMessage: String = notAnInteger("occupancy")
  }

  case object InvalidActionTaken extends ParserValidationError {
    override def errorMessage: String = notAnInteger("action taken")
  }

  case object InvalidActionTakenDate extends ParserValidationError {
    override def errorMessage: String = notAnInteger("action taken date")
  }


}
