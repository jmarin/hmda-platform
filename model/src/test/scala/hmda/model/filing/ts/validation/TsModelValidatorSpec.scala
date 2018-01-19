package hmda.model.filing.ts.validation

import cats.implicits._
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.filing.ts.TsGenerators._
import hmda.model.filing.ts.validation.TsModelValidator._
import hmda.model.filing.ts.validation.TsValidationErrorModel.TsStateCodeNotRecognized
import org.scalacheck.Gen

class TsModelValidatorSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  property("An address must be valid") {
    forAll(addressGen) { address =>
      validateAddress(address) mustBe address.validNel
    }
  }

  property("An address with malformed state abbreviation must be invalid") {
    forAll(addressGen) { address =>
      val badState = Gen.alphaStr.sample.getOrElse("XX")
      val badAddress = address.copy(state = badState)
      validateAddress(badAddress) mustBe TsStateCodeNotRecognized.invalidNel
    }
  }
}
