package hmda.model.filing.lar

import hmda.model.filing.PipeDelimited
import hmda.model.filing.lar.enums._

case class LoanApplicationRegister(
    larIdentifier: LarIdentifier,
    loan: Loan,
    action: LarAction,
    geography: Geography,
    applicant: Applicant,
    coApplicant: Applicant,
    income: String,
    purchaserType: PurchaserEnum,
    hoepaStatus: HOEPAStatusEnum,
    lienStatus: LienStatusEnum,
    denial: Denial,
    loanDisclosure: LoanDisclosure,
    nonAmortizingFeatures: NonAmortizingFeatures,
    property: Property,
    applicationSubmission: ApplicationSubmissionEnum,
    payableToInstitution: PayableToInstitutionEnum,
    AUS: Option[AutomatedUnderwritingSystem] = None,
    ausResult: Option[AutomatedUnderwritingSystemResult] = None,
    reverseMortgage: Option[MortgageTypeEnum] = None,
    lineOfCredit: Option[LineOfCreditEnum] = None,
    businessOrCommercialPurpose: Option[BusinessOrCommercialBusinessEnum] = None
) extends PipeDelimited {

  override def toCSV: String = {

    val manufacturedHomeSecuredPropertyStr =
      property.manufacturedHomeSecuredProperty match {
        case Some(homeSecured) => homeSecured.code
        case None              => ""
      }

    val manufacturedHomeLandStr =
      property.manufacturedHomeLandPropertyInterestEnum match {
        case Some(homeLandProperty) => homeLandProperty.code
        case None                   => ""
      }

    val ausStr = AUS match {
      case Some(aus) =>
        s"${aus.aus1}|${aus.aus2}|${aus.aus3}|${aus.aus4}|${aus.aus5}|${aus.otherAUS}"
      case None => "|||||"
    }

    val ausResultStr = ausResult match {
      case Some(result) =>
        s"${result.ausResult1}|${result.ausResult2}|${result.ausResult3}|${result.ausResult4}|${result.ausResult5}|${result.otherAusResult}"
      case None => "|||||"
    }

    val reverseMortgageStr = reverseMortgage match {
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

    s"${larIdentifier.id}|${larIdentifier.LEI.getOrElse("")}|${loan.ULI.getOrElse(
      "")}|${loan.applicationDate}|${loan.loanType.code}|${loan.loanPurpose.code}|${action.preapproval.code}|" +
      s"${loan.constructionMethod.code}|${loan.occupancy.code}|${loan.amount}|${action.actionTakenType.code}|${action.actionTakenDate}|" +
      s"${geography.street}|${geography.city}|${geography.state}|${geography.zipCode}|${geography.county}|${geography.tract}|" +
      s"${applicant.ethnicity.ethnicity1.code}|${applicant.ethnicity.ethnicity2.code}|${applicant.ethnicity.ethnicity3.code}|" +
      s"${applicant.ethnicity.ethnicity4.code}|${applicant.ethnicity.ethnicity5.code}|${applicant.ethnicity.otherHispanicOrLatino}|" +
      s"${coApplicant.ethnicity.ethnicity1.code}|${coApplicant.ethnicity.ethnicity2.code}|${coApplicant.ethnicity.ethnicity3.code}|" +
      s"${coApplicant.ethnicity.ethnicity4.code}|${coApplicant.ethnicity.ethnicity5.code}|${coApplicant.ethnicity.otherHispanicOrLatino}|" +
      s"${applicant.ethnicity.ethnicityObserved.code}|${coApplicant.ethnicity.ethnicityObserved.code}|${applicant.race.race1.code}|" +
      s"${applicant.race.race2.code}|${applicant.race.race3.code}|${applicant.race.race4.code}|${applicant.race.race5.code}|${applicant.race.otherNativeRace}|" +
      s"${applicant.race.otherAsianRace}|${applicant.race.otherPacificIslanderRace}|${coApplicant.race.race1.code}|${coApplicant.race.race2.code}|" +
      s"${coApplicant.race.race3.code}|${coApplicant.race.race4.code}|${coApplicant.race.race5.code}|${coApplicant.race.otherNativeRace}|${coApplicant.race.otherAsianRace}|" +
      s"${coApplicant.race.otherPacificIslanderRace}|${applicant.race.raceObserved.code}|${coApplicant.race.raceObserved.code}|" +
      s"${applicant.sex.sexEnum.code}|${coApplicant.sex.sexEnum.code}|${applicant.sex.sexObservedEnum.code}|${coApplicant.sex.sexObservedEnum.code}|" +
      s"${applicant.age}|${coApplicant.age}|$income|${purchaserType.code}|${loan.rateSpread}|${hoepaStatus.code}|${lienStatus.code}|${applicant.creditScore}|${coApplicant.creditScore}|" +
      s"${applicant.creditScoreType.code}|${applicant.otherCreditScoreModel}|${coApplicant.creditScoreType.code}|${coApplicant.otherCreditScoreModel}|" +
      s"${denial.denialReason1}|${denial.denialReason2}|${denial.denialReason3}|${denial.denialReason4}|${denial.otherDenialReason}|${loanDisclosure.totalLoanCosts}|" +
      s"${loanDisclosure.totalPointsAndFees}|${loanDisclosure.originationCharges}|${loanDisclosure.discountPoints}|${loanDisclosure.lenderCredits}|${loan.interestRate
        .getOrElse("")}|" +
      s"${loan.prepaymentPenaltyTerm}|${loan.debtToIncomeRatio.getOrElse("")}|${loan.loanToValueRatio
        .getOrElse("")}|${loan.loanTerm}|${loan.introductoryRatePeriod}|${nonAmortizingFeatures.balloonPayment.code}|" +
      s"${nonAmortizingFeatures.interestOnlyPayments.code}|${nonAmortizingFeatures.negativeAmortization.code}|${nonAmortizingFeatures.otherNonAmortizingFeatures.code}|" +
      s"${property.propertyValue}|$manufacturedHomeSecuredPropertyStr|$manufacturedHomeLandStr|${property.totalUnits}|${property.multiFamilyAffordableUnits
        .getOrElse("")}|${applicationSubmission.code}|" +
      s"${payableToInstitution.code}|${larIdentifier.NMLSRIdentifier}|$ausStr|$ausResultStr|$reverseMortgageStr|$lineOfCreditStr|$businessOrCommercialStr"

  }

}
