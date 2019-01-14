package hmda.parser.filing.lar

import hmda.parser.ParserErrorModel._

object LarParserErrorModel {

  case object InvalidId extends ParserValidationError {
    override def errorMessage: String = notNumeric("id")
  }

  case object InvalidApplicationDate extends ParserValidationError {
    override def errorMessage: String = notNumeric("application date")
  }

  case object InvalidLoanType extends ParserValidationError {
    override def errorMessage: String = notNumeric("loan type")
  }

  case object InvalidLoanPurpose extends ParserValidationError {
    override def errorMessage: String = notNumeric("loan purpose")
  }

  case object InvalidPreapproval extends ParserValidationError {
    override def errorMessage: String = notNumeric("preapproval")
  }

  case object InvalidConstructionMethod extends ParserValidationError {
    override def errorMessage: String = notNumeric("construction method")
  }

  case object InvalidOccupancy extends ParserValidationError {
    override def errorMessage: String = notNumeric("occupancy")
  }

  case object InvalidActionTaken extends ParserValidationError {
    override def errorMessage: String = notNumeric("action taken")
  }

  case object InvalidActionTakenDate extends ParserValidationError {
    override def errorMessage: String = notNumeric("action taken date")
  }

  case object InvalidAmount extends ParserValidationError {
    override def errorMessage: String = notNumeric("amount")
  }

  case object InvalidLoanTerm extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("loan term")
  }

  case object InvalidApplicantEthnicity extends ParserValidationError {
    override def errorMessage: String = notNumeric("applicant ethnicity")
  }

  case object InvalidApplicantEthnicityObserved extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("applicant ethnicity visual observation or surname")
  }

  case object InvalidCoApplicantEthnicity extends ParserValidationError {
    override def errorMessage: String = notNumeric("co-applicant ethnicity")
  }

  case object InvalidCoApplicantEthnicityObserved
      extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("co-applicant ethnicity visual observation or surname")
  }

  case object InvalidApplicantRace extends ParserValidationError {
    override def errorMessage: String = notNumeric("applicant race")
  }

  case object InvalidApplicantRaceObserved extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("applicant race visual observation or surname")
  }

  case object InvalidCoApplicantRace extends ParserValidationError {
    override def errorMessage: String = notNumeric("co-applicant race")
  }

  case object InvalidCoApplicantRaceObserved extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("co-applicant race visual observation or surname")
  }

  case object InvalidApplicantSex extends ParserValidationError {
    override def errorMessage: String = notNumeric("applicant sex")
  }

  case object InvalidApplicantSexObserved extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("applicant sex visual observation or surname")
  }

  case object InvalidCoApplicantSex extends ParserValidationError {
    override def errorMessage: String = notNumeric("co-applicant sex")
  }

  case object InvalidCoApplicantSexObserved extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("co-applicant sex visual observation or surname")
  }

  case object InvalidAge extends ParserValidationError {
    override def errorMessage: String = notNumeric("age")
  }

  case object InvalidApplicantCreditScore extends ParserValidationError {
    override def errorMessage: String = notNumeric("applicant credit score")
  }

  case object InvalidApplicantCreditScoreModel extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("applicant credit scoring model")
  }

  case object InvalidCoApplicantCreditScore extends ParserValidationError {
    override def errorMessage: String = notNumeric("co-applicant credit score")
  }

  case object InvalidCoApplicantCreditScoreModel extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("co-applicant credit scoring model")
  }

  case object InvalidIncome extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("income")
  }

  case object InvalidPurchaserType extends ParserValidationError {
    override def errorMessage: String = notNumeric("purchaser type")
  }

  case object InvalidRateSpread extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("rate spread")
  }

  case object InvalidHoepaStatus extends ParserValidationError {
    override def errorMessage: String = notNumeric("HOEPA status")
  }

  case object InvalidLienStatus extends ParserValidationError {
    override def errorMessage: String = notNumeric("lien status")
  }

  case object InvalidDenial extends ParserValidationError {
    override def errorMessage: String = notNumeric("denial")
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
    override def errorMessage: String =
      notEmptyStringOrNaOrExempt("discount points")
  }

  case object InvalidLenderCredits extends ParserValidationError {
    override def errorMessage: String =
      notEmptyStringOrNaOrExempt("lender credits")
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
    override def errorMessage: String = notNumeric("ballon payments")
  }

  case object InvalidInterestOnlyPayment extends ParserValidationError {
    override def errorMessage: String = notNumeric("interest only payment")
  }

  case object InvalidNegativeAmortization extends ParserValidationError {
    override def errorMessage: String = notNumeric("negative amortization")
  }

  case object InvalidOtherNonAmortizingFeatures extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("other non amortizing features")
  }

  case object InvalidPropertyValue extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("property value")
  }

  case object InvalidManufacturedHomeSecuredProperty
      extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("manufactured home secured property")
  }

  case object InvalidManufacturedHomeLandPropertyInterest
      extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("manufactured home land property interest")
  }

  case object InvalidTotalUnits extends ParserValidationError {
    override def errorMessage: String = notNumeric("total units")
  }

  case object InvalidMultifamilyUnits extends ParserValidationError {
    override def errorMessage: String = notStringOrNA("multifamily units")
  }

  case object InvalidApplicationSubmission extends ParserValidationError {
    override def errorMessage: String = notNumeric("application submission")
  }

  case object InvalidPayableToInstitution extends ParserValidationError {
    override def errorMessage: String = notNumeric("payable to institution")
  }

  case object InvalidNMLSRIdentifier extends ParserValidationError {
    override def errorMessage: String =
      notStringOrNAOrExempt("NMLSR identifier")
  }

  case object InvalidAutomatedUnderwritingSystem extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("automated underwriting system")
  }

  case object InvalidAutomatedUnderwritingSystemResult
      extends ParserValidationError {
    override def errorMessage: String =
      notNumeric("automated underwriting result")
  }

  case object InvalidMortgageType extends ParserValidationError {
    override def errorMessage: String = notNumeric("mortgage type")
  }

  case object InvalidLineOfCredit extends ParserValidationError {
    override def errorMessage: String = notNumeric("line of credit")
  }

  case object InvalidBusinessOrCommercial extends ParserValidationError {
    override def errorMessage: String = notNumeric("business or commercial")
  }

}
