package hmda.parser.filing.lar

import cats.data.ValidatedNel
import com.typesafe.config.ConfigFactory
import hmda.model.filing.lar.LoanApplicationRegister
import hmda.parser.ParserErrorModel.{
  IncorrectNumberOfFields,
  ParserValidationError
}
import cats.implicits._

sealed trait LarFormatValidator {

  val config = ConfigFactory.load()

  val numberOfFields = config.getInt("hmda.filing.lar.length")

  type LarParserValidationResult[A] = ValidatedNel[ParserValidationError, A]

  def validateLar(values: Seq[String])
    : LarParserValidationResult[LoanApplicationRegister] = {

    if (values.lengthCompare(numberOfFields) != 0) {
      IncorrectNumberOfFields(values.length, numberOfFields).invalidNel
    } else {
      validateLarValues()
    }

  }

  def validateLarValues(): LarParserValidationResult[LoanApplicationRegister] =
    ???

}

object LarFormatValidator extends LarFormatValidator
