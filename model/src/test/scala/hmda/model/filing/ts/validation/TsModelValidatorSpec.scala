package hmda.model.filing.ts.validation

import cats.implicits._
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.filing.ts.TsGenerators._
import hmda.model.filing.ts.validation.TsModelValidator._
import hmda.model.filing.ts.validation.TsValidationErrorModel.{
  TsInvalidStateCodeModel,
  TsInvalidZipCodeFormatModel
}
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
      validateAddress(badAddress) mustBe TsInvalidStateCodeModel.invalidNel
    }
  }

  property("An address with malformed zip code must be invalid") {
    forAll(addressGen) { address =>
      val badZipCode = Gen.alphaStr.sample.getOrElse("000")
      val badAddress = address.copy(zipCode = badZipCode)
      validateAddress(badAddress) mustBe TsInvalidZipCodeFormatModel.invalidNel
    }
  }
}
