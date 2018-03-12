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

  case object InvalidAmount extends ParserValidationError {
    override def errorMessage: String = notNumeric("amount")
  }

  case object InvalidLoanTerm extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("loan term")
  }

  case object InvalidEthnicity extends ParserValidationError {
    override def errorMessage: String = notAnInteger("ethnicity")
  }

  case object InvalidRace extends ParserValidationError {
    override def errorMessage: String = notAnInteger("race")
  }

  case object InvalidSex extends ParserValidationError {
    override def errorMessage: String = notAnInteger("sex")
  }

  case object InvalidAge extends ParserValidationError {
    override def errorMessage: String = notAnInteger("age")
  }

  case object InvalidCreditScore extends ParserValidationError {
    override def errorMessage: String = notAnInteger("credit score")
  }

  case object InvalidIncome extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("income")
  }

  case object InvalidPurchaserType extends ParserValidationError {
    override def errorMessage: String = notAnInteger("purchaser type")
  }

  case object InvalidRateSpread extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("rate spread")
  }

  case object InvalidHoepaStatus extends ParserValidationError {
    override def errorMessage: String = notAnInteger("HOEPA status")
  }

  case object InvalidLienStatus extends ParserValidationError {
    override def errorMessage: String = notAnInteger("lien status")
  }

  case object InvalidDenial extends ParserValidationError {
    override def errorMessage: String = notAnInteger("denial")
  }

  case object InvalidTotalLoanCosts extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("total loan costs")
  }

  case object InvalidPointsAndFees extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("points and fees")
  }

  case object InvalidOriginationCharges extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("origination charges")
  }

  case object InvalidDiscountPoints extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("discount points")
  }

  case object InvalidLenderCredits extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("lender credtis")
  }

  case object InvalidInterestRate extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("interest rate")
  }

  case object InvalidPrepaymentPenaltyTerm extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("prepayment penalty term")
  }

  case object InvalidDebtToIncomeRatio extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("debt to income ratio")
  }

  case object InvalidLoanToValueRatio extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("loan to value ratio")
  }

  case object InvalidIntroductoryRatePeriod extends ParserValidationError {
    override def errorMessage: String =
      notStringOrNA("introductory rate period")
  }

  case object InvalidBalloonPayment extends ParserValidationError {
    override def errorMessage: String = notAnInteger("ballon payments")
  }

  case object InvalidInterestOnlyPayment extends ParserValidationError {
    override def errorMessage: String = notAnInteger("interest only payment")
  }

  case object InvalidNegativeAmortization extends ParserValidationError {
    override def errorMessage: String = notAnInteger("negative amortization")
  }

  case object InvalidOtherNonAmortizingFeatures extends ParserValidationError {
    override def errorMessage: String =
      notAnInteger("other non amortizing features")
  }

  case object InvalidPropertyValue extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("property value")
  }

  case object InvalidManufacturedHomeSecuredProperty
      extends ParserValidationError {
    override def errorMessage: String =
      notAnInteger("manufactured home secured property")
  }

  case object InvalidManufacturedHomeLandPropertyInterest
      extends ParserValidationError {
    override def errorMessage: String =
      notAnInteger("manufactured home land property interest")
  }

  case object InvalidTotalUnits extends ParserValidationError {
    override def errorMessage: String = notAnInteger("total units")
  }

  case object InvalidMultifamilyUnits extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("multifamily units")
  }

  case object InvalidApplicationSubmission extends ParserValidationError {
    override def errorMessage: String = notAnInteger("application submission")
  }

  case object InvalidPayableToInstitution extends ParserValidationError {
    override def errorMessage: String = notAnInteger("payable to institution")
  }

  case object InvalidNMLSRIdentifier extends ParserValidationError {
    override def errorMessage: String = notAnInteger("NMLSR identifier")
  }

  case object InvalidAutomatedUnderwritingSystem extends ParserValidationError {
    override def errorMessage: String =
      notAnInteger("automated underwriting system")
  }

  case object InvalidMortgageType extends ParserValidationError {
    override def errorMessage: String = notAnInteger("mortgage type")
  }

  case object InvalidLineOfCredit extends ParserValidationError {
    override def errorMessage: String = notAnInteger("line of credit")
  }

  case object InvalidBusinessOrCommercial extends ParserValidationError {
    override def errorMessage: String = notAnInteger("business or commercial")
  }

}
