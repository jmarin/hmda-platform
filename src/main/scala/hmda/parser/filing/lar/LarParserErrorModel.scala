package hmda.parser.filing.lar

import hmda.parser.ParserErrorModel._

object LarParserErrorModel {

  case object InvalidId extends ParserValidationError {
    override def errorMessage: String = notAnInteger("id")
  }
}
