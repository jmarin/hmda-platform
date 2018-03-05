package hmda.model.filing.lar

import enums._
import hmda.model.filing.PipeDelimited

case class LoanApplicationRegister(
    id: Int = 2,
    LEI: Option[String] = None,
    loan: Loan,
    preapproval: PreapprovalEnum,
    actionTakenType: ActionTakenTypeEnum,
    actionTakenDate: Int,
    geography: Geography,
    applicant: Applicant,
    coApplicant: Applicant,
    purchaserType: PurchaserEnum,
    rateSpread: String,
    hoepaStatus: HOEPAStatusEnum,
    lienStatus: LienStatusEnum,
    denial: Denial,
    otherDenialReason: String,
    totalLoanCosts: String,
    totalPointsAndFees: String,
    originationCharges: Option[String] = None,
    discountPoints: Option[String] = None,
    lenderCredits: Option[String] = None,
    interestRate: Option[String] = None,
    prepaymentPenaltyTerm: String,
    debtToIncomeRatio: Option[String] = None,
    loanToValueRatio: Option[String] = None,
    introductoryRatePeriod: String,
    balloonPayment: BalloonPaymentEnum,
    interestOnlyPayments: InterestOnlyPaymentsEnum,
    negativeAmortization: NegativeAmortizationEnum,
    otherNonAmortizingFeatures: OtherNonAmortizingFeaturesEnum,
    propertyValue: String,
    manufacturedHomeSecuredProperty: Option[
      ManufacturedHomeSecuredPropertyEnum] = None,
    manufacturedHomeLandPropertyInterestEnum: Option[
      ManufacturedHomeLandPropertyInterestEnum] = None,
    totalUnits: Option[Int] = None,
    multiFamilyAffordableUnits: Option[String] = None,
    applicationSubmission: ApplicationSubmissionEnum,
    payableToInstitution: PayableToInstitutionEnum,
    NMLSRIdentified: String,
    AUS: Option[AutomatedUnderwritingSystem] = None,
    otherAUS: Option[String] = None,
    ausResult: Option[AutomatedUnderwritingSystemResult] = None,
    otherAusResult: Option[String] = None,
    reverseMortgage: Option[MortgageTypeEnum] = None,
    lineOfCredit: Option[LineOfCreditEnum] = None,
    businessOrCommercialPurpose: Option[BusinessOrCommercialBusinessEnum] = None
) extends PipeDelimited {
  override def toCSV: String = {
    val leiStr = LEI match {
      case Some(lei) => lei
      case None      => ""
    }

    val originationChargesStr = originationCharges match {
      case Some(charges) => charges
      case None          => ""
    }

    val discountPointsStr = discountPoints match {
      case Some(points) => points
      case None         => ""
    }

    val lenderCreditsStr = lenderCredits match {
      case Some(credits) => credits
      case None          => ""
    }

    val interestRateStr = interestRate match {
      case Some(rate) => rate
      case None       => ""
    }

    val debtToIncomeRatioStr = debtToIncomeRatio match {
      case Some(debt) => debt
      case None       => ""
    }

    val loanToValueRatioStr = loanToValueRatio match {
      case Some(loanRatio) => loanRatio
      case None            => ""
    }

    val manufacturedHomeSecuredPropertyStr =
      manufacturedHomeSecuredProperty match {
        case Some(homeSecured) => homeSecured.code
        case None              => ""
      }

    val manufacturedHomeLandStr =
      manufacturedHomeLandPropertyInterestEnum match {
        case Some(homeLandProperty) => homeLandProperty.code
        case None                   => ""
      }

    val totalUnitsStr = totalUnits match {
      case Some(units) => units.toString
      case None        => ""
    }

    val multiFamilyStr = multiFamilyAffordableUnits match {
      case Some(multifamily) => multifamily
      case None              => ""
    }

    val ausStr = AUS match {
      case Some(aus) => aus.toCSV
      case None      => ""
    }

    val otherAusStr = otherAUS match {
      case Some(aus) => aus
      case None      => ""
    }

    val ausResultStr = ausResult match {
      case Some(ausRes) => ausRes.toCSV
      case None         => ""
    }

    val otherAusResultStr = otherAusResult match {
      case Some(otherAus) => otherAus
      case None           => ""
    }

    val mortgageTypeStr = reverseMortgage match {
      case Some(mortgageType) => mortgageType.code
      case None               => ""
    }

    val lineOfCreditStr = lineOfCredit match {
      case Some(credit) => credit.code
      case None         => ""
    }

    val businessOrCommercialStr = businessOrCommercialPurpose match {
      case Some(businessOrCommercial) => businessOrCommercial.code
      case None                       => ""
    }

    s"$id|$leiStr|${loan.toCSV}|${preapproval.code}|${actionTakenType.code}|$actionTakenDate|${geography.toCSV}|" +
      s"${applicant.toCSV}|${purchaserType.code}|$rateSpread|${hoepaStatus.code}|${lienStatus.code}|${denial.toCSV}|" +
      s"$otherDenialReason|$totalLoanCosts|$totalPointsAndFees|$originationChargesStr|$discountPointsStr|$lenderCreditsStr|" +
      s"$interestRateStr|$prepaymentPenaltyTerm|$debtToIncomeRatioStr|$loanToValueRatioStr|$introductoryRatePeriod|" +
      s"${balloonPayment.code}|${interestOnlyPayments.code}|${negativeAmortization.code}|${otherNonAmortizingFeatures.code}|" +
      s"$propertyValue|$manufacturedHomeSecuredPropertyStr|$manufacturedHomeLandStr|$totalUnitsStr|$multiFamilyStr|" +
      s"${applicationSubmission.code}|${payableToInstitution.code}|$NMLSRIdentified|$ausStr|$otherAusStr|$ausResultStr|" +
      s"$otherAusResultStr|$mortgageTypeStr|$lineOfCreditStr|$businessOrCommercialStr"
  }
}