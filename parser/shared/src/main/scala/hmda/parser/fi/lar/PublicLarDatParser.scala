package hmda.parser.fi.lar

import hmda.model.fi.lar.{ Loan, LoanApplicationRegister }

import scala.util.{ Failure, Success, Try }

// Parses public LAR data
object PublicLarDatParser {
  def apply(s: String): LoanApplicationRegister = {

    val respId = s.substring(4, 14)
    val code = toSafeInt(s.substring(14, 15))
    val loanType = toSafeInt(s.substring(15, 16))
    val loanPurpose = toSafeInt(s.substring(16, 17))
    val occupancy = toSafeInt(s.substring(17, 18))
    val amount = toSafeInt(s.substring(18, 23))
    val actionTakenType = toSafeInt(s.substring(23, 24))

    val loan = Loan(
      "",
      "",
      loanType,
      0,
      loanPurpose,
      occupancy,
      amount
    )

    LoanApplicationRegister(
      respondentId = respId,
      agencyCode = code,
      loan = loan
    )

  }

  private def toSafeInt(s: String): Int = {
    Try(s.toInt) match {
      case Success(i) => i
      case Failure(_) => throw new Exception(s"Cannot convert $s to Integer")
    }
  }
}
