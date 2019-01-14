package hmda.validation.rules.lar.validity

import hmda.model.filing.lar.LarGenerators._
import hmda.model.filing.lar.LoanApplicationRegister
import hmda.validation.rules.EditCheck
import hmda.validation.rules.lar.LarEditCheckSpec

class V626Spec extends LarEditCheckSpec {
  override def check: EditCheck[LoanApplicationRegister] = V626

  property("Census County must be valid") {
    forAll(larGen) { lar =>
      val unappLar = lar.copy(
        geography = lar.geography.copy(county = "NA")
      )
      unappLar.mustPass

      val appLar = lar.copy(geography = lar.geography.copy(county = "1"))
      appLar.mustFail

      val emptyCounty = lar.copy(geography = lar.geography.copy(county = ""))
      emptyCounty.mustFail
    }
  }
}
