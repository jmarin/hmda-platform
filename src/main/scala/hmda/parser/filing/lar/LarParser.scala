package hmda.parser.filing.lar

import cats.data.ValidatedNel
import hmda.model.filing.lar.enums.LarCodeEnum
import hmda.parser.ParserErrorModel.ParserValidationError
import cats.implicits._
import scala.util.{Failure, Success, Try}

trait LarParser {

  type LarParserValidationResult[A] = ValidatedNel[ParserValidationError, A]

  def validateIntField(value: String,
                       parserValidationError: ParserValidationError)
    : LarParserValidationResult[Int] = {
    Try(value.toInt) match {
      case Success(i) => i.validNel
      case Failure(_) => parserValidationError.invalidNel
    }
  }

  def validateDoubleField(value: String,
                          parserValidationError: ParserValidationError)
    : LarParserValidationResult[Double] = {
    Try(value.toDouble) match {
      case Success(i) => i.validNel
      case Failure(_) => parserValidationError.invalidNel
    }
  }

  def validateStrOrNAField(value: String,
                           parserValidationError: ParserValidationError)
    : LarParserValidationResult[String] = {
    if (value == "") {
      parserValidationError.invalidNel
    } else if (value == "NA") {
      value.validNel
    } else {
      validateIntField(value, parserValidationError).map(x => x.toString)
    }
  }

  def validateStr(str: String): LarParserValidationResult[String] = {
    str.validNel
  }

  def validateMaybeStr(
      str: String): LarParserValidationResult[Option[String]] = {
    if (str.equals("")) {
      None.validNel
    } else {
      Some(str).validNel
    }
  }

  def validateMaybeStrOrNAField(value: String,
                                parserValidationError: ParserValidationError)
    : LarParserValidationResult[Option[String]] = {
    if (value == "") {
      parserValidationError.invalidNel
    } else if (value == "NA") {
      Some(value).validNel
    } else {
      validateIntField(value, parserValidationError).map(x => Some(x.toString))
    }
  }

  def validateLarCode[A](larCodeEnum: LarCodeEnum[A],
                         value: String,
                         parserValidationError: ParserValidationError)
    : LarParserValidationResult[A] = {
    Try(larCodeEnum.valueOf(value.toInt)) match {
      case Success(enum) => enum.validNel
      case Failure(_)    => parserValidationError.invalidNel
    }
  }

}
