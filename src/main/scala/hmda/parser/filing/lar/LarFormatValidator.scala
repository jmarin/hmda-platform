package hmda.parser.filing.lar

import cats.data.ValidatedNel
import com.typesafe.config.ConfigFactory
import hmda.model.filing.lar._
import hmda.parser.ParserErrorModel.{
  IncorrectNumberOfFields,
  ParserValidationError
}
import cats.implicits._
import hmda.model.filing.lar.enums._
import hmda.parser.filing.lar.LarParserErrorModel._
import ApplicantFormatValidator._

import scala.util.{Failure, Success, Try}

case class TempLAR(
    id: Int,
    LEI: String,
    ULI: Option[String],
    applicationDate: Int,
    geography: Geography,
    preapprovalEnum: PreapprovalEnum,
    actionTakenTypeEnum: ActionTakenTypeEnum
)

sealed trait LarFormatValidator extends LarParser {

  val config = ConfigFactory.load()

  val numberOfFields = config.getInt("hmda.filing.lar.length")

  def validateLar(values: Seq[String])
    : LarParserValidationResult[LoanApplicationRegister] = {

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
      val lienStatus = values(61)
      val appCreditScore = values(62)
      val coAppCreditScore = values(63)
      val appCreditScoreModel = values(64)
      val appCreditScoreModelOther = values(65)
      val coAppCreditScoreModel = values(66)
      val coAppCreditScoreModelOther = values(67)
      val denial1 = values(68)
      val denial2 = values(69)
      val denial3 = values(70)
      val denial4 = values(71)
      val denialOther = values(72)
      val totalLoanCosts = values(73)
      val totalPointsAndFees = values(74)
      val originationCharges = values(75)
      val discountPoints = values(76)
      val lenderCredits = values(77)
      val interestRate = values(78)
      val prepaymentPenaltyTerm = values(79)
      val debtToIncomeRatio = values(80)
      val loanToValueRatio = values(81)
      val loanTerm = values(82)
      val introductoryRatePeriod = values(83)
      val balloonPayment = values(84)
      val interestOnlyPayment = values(85)
      val negativeAmortization = values(86)
      val otherNonAmortizingFeatures = values(87)
      val propertyValue = values(88)
      val manufacturedHomeSecuredProperty = values(89)
      val manufacturedHomeLandPropertyInterest = values(90)
      val totalUnits = values(91)
      val multifamilyAffordableUnits = values(92)
      val submissionOfApplication = values(93)
      val payableToInstitution = values(94)
      val nmlsrIdentifier = values(95)
      val aus1 = values(96)
      val aus2 = values(97)
      val aus3 = values(98)
      val aus4 = values(99)
      val aus5 = values(100)
      val ausOther = values(101)
      val ausResult1 = values(102)
      val ausResult2 = values(103)
      val ausResult3 = values(104)
      val ausResult4 = values(105)
      val ausResult5 = values(106)
      val ausResultOther = values(107)
      val reverseMortgate = values(108)
      val openEndLineOfCredit = values(109)
      val businessOrCommercial = values(110)

      validateLarValues(
        id,
        lei,
        uli,
        applicationDate,
        loanType,
        loanPurpose,
        preapproval,
        constructionMethod,
        occupancy,
        loanAmount,
        actionTaken,
        actionTakenDate,
        street,
        city,
        state,
        zipCode,
        county,
        tract,
        appEth1,
        appEth2,
        appEth3,
        appEth4,
        appEth5,
        appEthOther,
        coAppEth1,
        coAppEth2,
        coAppEth3,
        coAppEth4,
        coAppEth5,
        coAppEthOther,
        appEthObserved,
        coAppEthObserved,
        appRace1,
        appRace2,
        appRace3,
        appRace4,
        appRace5,
        appOtherNative,
        appOtherAsian,
        appOtherPacific,
        coAppRace1,
        coAppRace2,
        coAppRace3,
        coAppRace4,
        coAppRace5,
        coAppOtherNative,
        coAppOtherAsian,
        coAppOtherPacific,
        appRaceObserved,
        coAppRaceObserved,
        appSex,
        coAppSex,
        appSexObserved,
        coAppSexObserved,
        appAge,
        coAppAge,
        income,
        purchaserType,
        rateSpread,
        hoepaStatus,
        lienStatus,
        appCreditScore,
        coAppCreditScore,
        appCreditScoreModel,
        appCreditScoreModelOther,
        coAppCreditScoreModel,
        coAppCreditScoreModelOther,
        denial1,
        denial2,
        denial3,
        denial4,
        denialOther,
        totalLoanCosts,
        totalPointsAndFees,
        originationCharges,
        discountPoints,
        lenderCredits,
        interestRate,
        prepaymentPenaltyTerm,
        debtToIncomeRatio,
        loanToValueRatio,
        loanTerm,
        introductoryRatePeriod,
        balloonPayment,
        interestOnlyPayment,
        negativeAmortization,
        otherNonAmortizingFeatures,
        propertyValue,
        manufacturedHomeSecuredProperty,
        manufacturedHomeLandPropertyInterest,
        totalUnits,
        multifamilyAffordableUnits,
        submissionOfApplication,
        payableToInstitution,
        nmlsrIdentifier,
        aus1,
        aus2,
        aus3,
        aus4,
        aus5,
        ausOther,
        ausResult1,
        ausResult2,
        ausResult3,
        ausResult4,
        ausResult5,
        ausResultOther,
        reverseMortgate,
        openEndLineOfCredit,
        businessOrCommercial
      )
    }

  }

  def validateLarValues(
      id: String,
      lei: String,
      uli: String,
      applicationDate: String,
      loanType: String,
      loanPurpose: String,
      preapproval: String,
      constructionMethod: String,
      occupancy: String,
      loanAmount: String,
      actionTaken: String,
      actionTakenDate: String,
      street: String,
      city: String,
      state: String,
      zipCode: String,
      county: String,
      tract: String,
      appEth1: String,
      appEth2: String,
      appEth3: String,
      appEth4: String,
      appEth5: String,
      appEthOther: String,
      coAppEth1: String,
      coAppEth2: String,
      coAppEth3: String,
      coAppEth4: String,
      coAppEth5: String,
      coAppEthOther: String,
      appEthObserved: String,
      coAppEthObserved: String,
      appRace1: String,
      appRace2: String,
      appRace3: String,
      appRace4: String,
      appRace5: String,
      appOtherNative: String,
      appOtherAsian: String,
      appOtherPacific: String,
      coAppRace1: String,
      coAppRace2: String,
      coAppRace3: String,
      coAppRace4: String,
      coAppRace5: String,
      coAppOtherNative: String,
      coAppOtherAsian: String,
      coAppOtherPacific: String,
      appRaceObserved: String,
      coAppRaceObserved: String,
      appSex: String,
      coAppSex: String,
      appSexObserved: String,
      coAppSexObserved: String,
      appAge: String,
      coAppAge: String,
      income: String,
      purchaserType: String,
      rateSpread: String,
      hoepaStatus: String,
      lienStatus: String,
      appCreditScore: String,
      coAppCreditScore: String,
      appCreditScoreModel: String,
      appCreditScoreModelOther: String,
      coAppCreditScoreModel: String,
      coAppCreditScoreModelOther: String,
      denial1: String,
      denial2: String,
      denial3: String,
      denial4: String,
      denialOther: String,
      totalLoanCosts: String,
      totalPointsAndFees: String,
      originationCharges: String,
      discountPoints: String,
      lenderCredits: String,
      interestRate: String,
      prepaymentPenaltyTerm: String,
      debtToIncomeRatio: String,
      loanToValueRatio: String,
      loanTerm: String,
      introductoryRatePeriod: String,
      balloonPayment: String,
      interestOnlyPayment: String,
      negativeAmortization: String,
      otherNonAmortizingFeatures: String,
      propertyValue: String,
      manufacturedHomeSecuredProperty: String,
      manufacturedHomeLandPropertyInterest: String,
      totalUnits: String,
      multifamilyAffordableUnits: String,
      submissionOfApplication: String,
      payableToInstitution: String,
      nmlsrIdentifier: String,
      aus1: String,
      aus2: String,
      aus3: String,
      aus4: String,
      aus5: String,
      ausOther: String,
      ausResult1: String,
      ausResult2: String,
      ausResult3: String,
      ausResult4: String,
      ausResult5: String,
      ausResultOther: String,
      reverseMortgage: String,
      openEndLineOfCredit: String,
      businessOrCommercial: String
  ) = {

    (
      validateIntField(id, InvalidId),
      validateMaybeStr(lei),
      validateLoan(uli,
                   applicationDate,
                   loanType,
                   loanPurpose,
                   constructionMethod,
                   occupancy,
                   loanAmount,
                   loanTerm),
      validateLarCode(PreapprovalEnum, preapproval, InvalidPreapproval),
      validateLarCode(ActionTakenTypeEnum, actionTaken, InvalidActionTaken),
      validateIntField(actionTakenDate, InvalidActionTakenDate),
      validateGeography(street, city, state, zipCode, county, tract),
      validateApplicant(
        appEth1,
        appEth2,
        appEth3,
        appEth4,
        appEth5,
        appEthOther,
        appEthObserved,
        appRace1,
        appRace2,
        appRace3,
        appRace4,
        appRace5,
        appOtherNative,
        appOtherAsian,
        appOtherPacific,
        appRaceObserved,
        appSex,
        appSexObserved,
        appAge,
        appCreditScore,
        appCreditScoreModel,
        appCreditScoreModelOther
      ),
      validateApplicant(
        coAppEth1,
        coAppEth2,
        coAppEth3,
        coAppEth4,
        coAppEth5,
        coAppEthOther,
        coAppEthObserved,
        coAppRace1,
        coAppRace2,
        coAppRace3,
        coAppRace4,
        coAppRace5,
        coAppOtherNative,
        coAppOtherAsian,
        coAppOtherPacific,
        coAppRaceObserved,
        coAppSex,
        coAppSexObserved,
        coAppAge,
        coAppCreditScore,
        coAppCreditScoreModel,
        coAppCreditScoreModelOther
      ),
      validateStrOrNAField(income, InvalidIncome),
      validateLarCode(PurchaserEnum, purchaserType, InvalidPurchaserType),
      validateStrOrNAField(rateSpread, InvalidRateSpread),
      validateLarCode(HOEPAStatusEnum, hoepaStatus, InvalidHoepaStatus),
      validateLarCode(LienStatusEnum, lienStatus, InvalidLienStatus),
      validateDenial(denial1, denial2, denial3, denial4, denialOther),
      validateLoanDisclosure(totalLoanCosts,
                             totalPointsAndFees,
                             originationCharges,
                             discountPoints,
                             lenderCredits),
      validateMaybeStrOrNAField(interestRate, InvalidInterestRate),
      validateStrOrNAField(prepaymentPenaltyTerm, InvalidPrepaymentPenaltyTerm),
      validateMaybeStrOrNAField(debtToIncomeRatio, InvalidDebtToIncomeRatio),
      validateMaybeStrOrNAField(loanToValueRatio, InvalidLoanToValueRatio),
      validateMaybeStrOrNAField(introductoryRatePeriod,
                                InvalidIntroductoryRatePeriod),
      validateNonAmortizingFeatures(balloonPayment,
                                    interestOnlyPayment,
                                    negativeAmortization,
                                    otherNonAmortizingFeatures),
      validateStrOrNAField(propertyValue, InvalidPropertyValue),
      validateLarCode(ManufacturedHomeSecuredPropertyEnum,
                      manufacturedHomeSecuredProperty,
                      InvalidManufacturedHomeSecuredProperty).map(x => Some(x)),
      validateLarCode(ManufacturedHomeLandPropertyInterestEnum,
                      manufacturedHomeLandPropertyInterest,
                      InvalidManufacturedHomeLandPropertyInterest).map(x =>
        Some(x)),
      validateIntField(totalUnits, InvalidTotalUnits).map(i => Some(i)),
      validateStrOrNAField(multifamilyAffordableUnits, InvalidMultifamilyUnits),
      validateLarCode(ApplicationSubmissionEnum,
                      submissionOfApplication,
                      InvalidApplicationSubmission),
      validateLarCode(PayableToInstitutionEnum,
                      payableToInstitution,
                      InvalidPayableToInstitution),
      validateStrOrNAField(nmlsrIdentifier, InvalidNMLSRIdentifier),
      validateAUS(aus1, aus2, aus3, aus4, aus5, ausOther).map(x => Some(x)),
      validateLarCode(MortgageTypeEnum, reverseMortgage, InvalidMortgageType)
        .map(x => Some(x)),
      validateLarCode(LineOfCreditEnum,
                      openEndLineOfCredit,
                      InvalidLineOfCredit).map(x => Some(x)),
      validateLarCode(BusinessOrCommercialBusinessEnum,
                      businessOrCommercial,
                      InvalidBusinessOrCommercial)
    ).mapN(LoanApplicationRegister)
  }

  private def validateLoan(
      uli: String,
      applicationDate: String,
      loanType: String,
      loanPurpose: String,
      constructionMethod: String,
      occupancy: String,
      amount: String,
      loanTerm: String
  ): LarParserValidationResult[Loan] = {
    (
      validateMaybeStr(uli),
      validateStr(applicationDate),
      validateLarCode(LoanTypeEnum, loanType, InvalidLoanType),
      validateLarCode(LoanPurposeEnum, loanPurpose, InvalidLoanPurpose),
      validateLarCode(ConstructionMethodEnum,
                      constructionMethod,
                      InvalidConstructionMethod),
      validateLarCode(OccupancyEnum, occupancy, InvalidOccupancy),
      validateDoubleField(amount, InvalidAmount),
      validateStr(loanTerm)
    ).mapN(Loan)
  }

  private def validateGeography(
      street: String,
      city: String,
      state: String,
      zipCode: String,
      county: String,
      tract: String): LarParserValidationResult[Geography] = {
    (
      validateStr(street),
      validateStr(city),
      validateStr(state),
      validateStr(zipCode),
      validateStr(county),
      validateStr(tract)
    ).mapN(Geography)
  }

  private def validateDenial(
      denial1: String,
      denial2: String,
      denial3: String,
      denial4: String,
      otherDenial: String
  ): LarParserValidationResult[Denial] = {

    (
      validateLarCode(DenialReasonEnum, denial1, InvalidDenial),
      validateLarCode(DenialReasonEnum, denial2, InvalidDenial),
      validateLarCode(DenialReasonEnum, denial3, InvalidDenial),
      validateLarCode(DenialReasonEnum, denial4, InvalidDenial),
      validateStr(otherDenial)
    ).mapN(Denial)
  }

  private def validateLoanDisclosure(
      totalLoanCosts: String,
      totalPointsAndFees: String,
      originationCharges: String,
      discountPoints: String,
      lenderCredits: String
  ): LarParserValidationResult[LoanDisclosure] = {
    (
      validateStrOrNAField(totalLoanCosts, InvalidTotalLoanCosts),
      validateStrOrNAField(totalPointsAndFees, InvalidPointsAndFees),
      validateMaybeStrOrNAField(originationCharges, InvalidOriginationCharges),
      validateMaybeStrOrNAField(discountPoints, InvalidDiscountPoints),
      validateMaybeStrOrNAField(lenderCredits, InvalidLenderCredits)
    ).mapN(LoanDisclosure)
  }

  private def validateNonAmortizingFeatures(
      ballonPayment: String,
      interestOnlyPayment: String,
      negativeAmortization: String,
      otherNonAmortizingFeatures: String
  ): LarParserValidationResult[NonAmortizingFeatures] = {
    (
      validateLarCode(BalloonPaymentEnum, ballonPayment, InvalidBalloonPayment),
      validateLarCode(InterestOnlyPaymentsEnum,
                      interestOnlyPayment,
                      InvalidInterestOnlyPayment),
      validateLarCode(NegativeAmortizationEnum,
                      negativeAmortization,
                      InvalidNegativeAmortization),
      validateLarCode(OtherNonAmortizingFeaturesEnum,
                      otherNonAmortizingFeatures,
                      InvalidOtherNonAmortizingFeatures)
    ).mapN(NonAmortizingFeatures)

  }

  private def validateAUS(
      aus1: String,
      aus2: String,
      aus3: String,
      aus4: String,
      aus5: String,
      otherAus: String
  ): LarParserValidationResult[AutomatedUnderwritingSystem] = {
    (
      validateLarCode(AutomatedUnderwritingSystemEnum,
                      aus1,
                      InvalidAutomatedUnderwritingSystem),
      validateLarCode(AutomatedUnderwritingSystemEnum,
                      aus2,
                      InvalidAutomatedUnderwritingSystem),
      validateLarCode(AutomatedUnderwritingSystemEnum,
                      aus3,
                      InvalidAutomatedUnderwritingSystem),
      validateLarCode(AutomatedUnderwritingSystemEnum,
                      aus4,
                      InvalidAutomatedUnderwritingSystem),
      validateLarCode(AutomatedUnderwritingSystemEnum,
                      aus5,
                      InvalidAutomatedUnderwritingSystem),
      validateMaybeStr(otherAus)
    ).mapN(AutomatedUnderwritingSystem)
  }

}

object LarFormatValidator extends LarFormatValidator
