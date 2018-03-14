package hmda.parser.filing.lar

import com.typesafe.config.ConfigFactory
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import LarFormatValidator._
import cats.data.NonEmptyList
import cats.data.Validated.Invalid
import hmda.parser.ParserErrorModel.IncorrectNumberOfFields
import hmda.model.filing.lar.LarGenerators._
import LarValidationUtils._
import hmda.parser.filing.lar.LarParserErrorModel.InvalidId

class LarFormatValidatorSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  val config = ConfigFactory.load()

  val numberOfFields = config.getInt("hmda.filing.lar.length")

  property("Loan Application Register must have the correct number of fields") {
    val values = List("a", "b", "c")
    validateLar(values) mustBe Invalid(
      NonEmptyList.of(IncorrectNumberOfFields(values.length, numberOfFields))
    )
  }

  property(
    "Loan Application Register must report InvalidId for non numeric field value") {
    forAll(larGen) { lar =>
      val badId = badValue()
      val values = extractValues(lar).updated(0, badId)
      validateLar(values) mustBe Invalid(NonEmptyList.of(InvalidId))
    }
  }

  property("Invalid Application Date") {
    pending
  }

  property("InvalidLoanType") {
    pending
  }
  property("InvalidLoanPurpose") {
    pending
  }
  property("InvalidPreapproval") {
    pending
  }
  property("InvalidConstructionMethod") {
    pending
  }
  property("InvalidOccupancy") {
    pending
  }
  property("InvalidActionTaken") {
    pending
  }
  property("InvalidActionTakenDate") {
    pending
  }
  property("InvalidAmount") {
    pending
  }
  property("InvalidLoanTerm") {
    pending
  }
  property("InvalidIncome") {
    pending
  }
  property("InvalidPurchaserType") {
    pending
  }
  property("InvalidRateSpread") {
    pending
  }
  property("InvalidHoepaStatus") {
    pending
  }
  property("InvalidLienStatus") {
    pending
  }
  property("InvalidDenial") {
    pending
  }
  property("InvalidTotalLoanCosts") {
    pending
  }
  property("InvalidPointsAndFees") {
    pending
  }
  property("InvalidOriginationCharges") {
    pending
  }
  property("InvalidDiscountPoints") {
    pending
  }
  property("InvalidLenderCredits") {
    pending
  }
  property("InvalidInterestRate") {
    pending
  }
  property("InvalidPrepaymentPenaltyTerm") {
    pending
  }
  property("InvalidDebtToIncomeRatio") {
    pending
  }
  property("InvalidLoanToValueRatio") {
    pending
  }
  property("InvalidIntroductoryRatePeriod") {
    pending
  }
  property("InvalidBalloonPayment") {
    pending
  }
  property("InvalidInterestOnlyPayment") {
    pending
  }
  property("InvalidNegativeAmortization") {
    pending
  }
  property("InvalidOtherNonAmortizingFeatures") {
    pending
  }
  property("InvalidPropertyValue") {
    pending
  }
  property("InvalidManufacturedHomeSecuredProperty") {
    pending
  }
  property("InvalidManufacturedHomeLandPropertyInterest") {
    pending
  }
  property("InvalidTotalUnits") {
    pending
  }
  property("InvalidMultifamilyUnits") {
    pending
  }
  property("InvalidApplicationSubmission") {
    pending
  }
  property("InvalidPayableToInstitution") {
    pending
  }
  property("InvalidNMLSRIdentifier") {
    pending
  }
  property("InvalidAutomatedUnderwritingSystem") {
    pending
  }
  property("InvalidAutomatedUnderwritingSystemResult") {
    pending
  }
  property("InvalidMortgageType") {
    pending
  }
  property("InvalidLineOfCredit") {
    pending
  }
  property("InvalidBusinessOrCommercial") {
    pending
  }

}
