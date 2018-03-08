package hmda.parser.filing.lar

import cats.data.ValidatedNel
import com.typesafe.config.ConfigFactory
import hmda.model.filing.lar.{Geography, LoanApplicationRegister}
import hmda.parser.ParserErrorModel.{IncorrectNumberOfFields, ParserValidationError}
import cats.implicits._
import hmda.model.filing.lar.enums._
import hmda.parser.filing.lar.LarParserErrorModel.{InvalidApplicationDate, InvalidId}

import scala.util.{Failure, Success, Try}

case class TempLAR(
                    id: Int,
                    LEI: String,
                    ULI: Option[String],
                    applicationDate: Int,
                    geography: Geography
                  )

sealed trait LarFormatValidator {

  val config = ConfigFactory.load()

  val numberOfFields = config.getInt("hmda.filing.lar.length")

  type LarParserValidationResult[A] = ValidatedNel[ParserValidationError, A]

  def validateLar(values: Seq[String]): LarParserValidationResult[TempLAR] = {

    if (values.lengthCompare(numberOfFields) != 0) {
      IncorrectNumberOfFields(values.length, numberOfFields).invalidNel
    } else {
      val id = values.headOption.getOrElse("")
      val lei = values(1)
      val uli = values(2)
      val applicationDate = values(3)
      val loanType = values(4)
      val loanPurpose = values(5)
      val preapproval = values(6)
      val constructionMethod = values(7)
      val occupancy = values(8)
      val loanAmount = values(9)
      val actionTaken = values(10)
      val actionTakenDate = values(11)
      val street = values(12)
      val city = values(13)
      val state = values(14)
      val zipCode = values(15)
      val county = values(16)
      val tract = values(17)
      val appEth1 = values(18)
      val appEth2 = values(19)
      val appEth3 = values(20)
      val appEth4 = values(21)
      val appEth5 = values(22)
      val appEthOther = values(24)
      val coAppEth1 = values(25)
      val coAppEth2 = values(26)
      val coAppEth3 = values(27)
      val coAppEth4 = values(28)
      val coAppEth5 = values(29)
      val coAppEthOther = values(30)
      val appEthObserved = values(31)
      val coAppEthObserved = values(32)
      val appRace1 = values(33)
      val appRace2 = values(34)
      val appRace3 = values(35)
      val appRace4 = values(36)
      val appRace5 = values(37)
      val appOtherNative = values(38)
      val appOtherAsian = values(39)
      val appOtherPacific = values(40)
      val coAppRace1 = values(41)
      val coAppRace2 = values(42)
      val coAppRace3 = values(43)
      val coAppRace4 = values(44)
      val coAppRace5 = values(45)
      val coAppOtherNative = values(46)
      val coAppOtherAsian = values(47)
      val coAppOtherPacific = values(48)
      val appRaceObserved = values(49)
      val coAppRaceObserved = values(50)
      val appSex = values(51)
      val coAppSex = values(52)
      val appSexObserved = values(53)
      val coAppSexObserved = values(54)
      val appAge = values(55)
      val coAppAge = values(56)
      val income = values(57)
      val purchaserType = values(58)
      val rateSpread = values(59)
      val hoepaStatus = values(60)
      val appCreditScore = values(61)
      val coAppCreditScore = values(62)


      val geography = Geography(street, city, state, zipCode, county, tract)

      validateLarValues(
        id,
        lei,
        uli,
        applicationDate,
        geography)
    }

  }

  def validateLarValues(
      id: String,
      lei: String,
      uli: String,
      applicationDate: String,

      geography: Geography
//      loanType: String,
//      loanPurpose: String,
//      preapproval: String,
//      constructionMethod: String,
//      occupancy: String,
//      loanAmount: String,
  ): LarParserValidationResult[TempLAR] = {

    (
      validateIdField(id),
      validateStr(lei),
      validateMaybeStr(uli),
      validateApplicationDate(applicationDate),
      validateGeography(geography)
    ).mapN(TempLAR)

  }

  private def validateApplicationDate(value: String): LarParserValidationResult[Int] = {
    validateIntField(value, InvalidApplicationDate)
  }

  private def validateStr(str: String): LarParserValidationResult[String] = {
    str.validNel
  }

  private def validateMaybeStr(
      str: String): LarParserValidationResult[Option[String]] = {
    if (str.equals("")) {
      None.validNel
    } else {
      Some(str).validNel
    }
  }

  private def validateIdField(value: String): LarParserValidationResult[Int] = {
    validateIntField(value, InvalidId)
  }

  private def validateGeography(geography: Geography): LarParserValidationResult[Geography] = {
    (
      validateStr(geography.street),
      validateStr(geography.city),
      validateStr(geography.state),
      validateStr(geography.zipCode),
      validateStr(geography.county),
      validateStr(geography.tract)
    ).mapN(Geography)
  }

  private def validateIntField(value: String,
                               parserValidation: ParserValidationError)
    : LarParserValidationResult[Int] = {
    Try(value.toInt) match {
      case Success(i) => i.validNel
      case Failure(_) => parserValidation.invalidNel
    }
  }
}

object LarFormatValidator extends LarFormatValidator
