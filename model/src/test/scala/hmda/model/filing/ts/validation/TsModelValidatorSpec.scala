package hmda.model.filing.ts.validation

import cats.data.Validated.Valid
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.filing.ts.TsGenerators._
import hmda.model.filing.ts.validation.TsModelValidator._

class TsModelValidatorSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  property("An address must be valid") {
    forAll(addressGen) { address =>
      validateAddress(address) mustBe Valid(address)
    }
  }
}
