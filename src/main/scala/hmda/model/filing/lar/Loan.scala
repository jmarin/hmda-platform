package hmda.model.filing.lar

import hmda.model.filing.lar.enums.{
  ConstructionMethodEnum,
  LoanPurposeEnum,
  LoanTypeEnum,
  OccupancyEnum
}

case class Loan(
    ULI: Option[String] = None,
    applicationDate: String,
    loanType: LoanTypeEnum,
    loanPurpose: LoanPurposeEnum,
    constructionMethod: ConstructionMethodEnum,
    occupancy: OccupancyEnum,
    amount: Double,
    loanTerm: String,
    rateSpread: String,
    interestRate: Option[String] = None,
    prepaymentPenaltyTerm: String,
    debtToIncomeRatio: Option[String] = None,
    loanToValueRatio: Option[String] = None,
    introductoryRatePeriod: String
)
