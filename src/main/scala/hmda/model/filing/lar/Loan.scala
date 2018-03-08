package hmda.model.filing.lar

import hmda.model.filing.lar.enums._

case class Loan(
    ULI: Option[String] = None,
    applicationDate: String,
    loanType: LoanTypeEnum,
    loanPurpose: LoanPurposeEnum,
    constructionMethod: ConstructionMethodEnum,
    occupancy: OccupancyEnum,
    amount: Double,
    loanTerm: String
)

object Loan {
  def empty: Loan = {
    Loan(None,
         "",
         Conventional,
         HomePurchase,
         SiteBuilt,
         PrincipalResidence,
         0.0,
         "")
  }
}
