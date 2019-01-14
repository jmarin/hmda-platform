package hmda.validation.rules.lar

import hmda.model.filing.lar.LoanApplicationRegister
import hmda.validation.dsl.{ValidationFailure, ValidationSuccess}
import hmda.validation.rules.EditCheck
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks

abstract class LarEditCheckSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  implicit val generatorDriverConfig =
    PropertyCheckConfiguration(minSuccessful = 100, maxDiscardedFactor = 5.0)

  def check: EditCheck[LoanApplicationRegister]

  implicit class LarChecker(lar: LoanApplicationRegister) {
    def mustFail = check(lar) mustBe a[ValidationFailure.type]
    def mustPass = check(lar) mustBe a[ValidationSuccess.type]
  }
}
