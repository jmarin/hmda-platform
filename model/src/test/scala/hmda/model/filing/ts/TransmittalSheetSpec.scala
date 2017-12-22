package hmda.model.filing.ts

import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.filing.ts.TsGenerators._

class TransmittalSheetSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  property("must return value from friendly field name: Tax ID") {
    forAll(tsGen) { (ts: TransmittalSheet) =>
      ts.valueOf("Tax ID") mustBe ts.taxId
    }
  }

  property(
    "must return value from friendly field name: Financial Institution Name") {
    forAll(tsGen) { (ts: TransmittalSheet) =>
      ts.valueOf("Financial Institution Name") mustBe ts.institutionName
    }
  }

  property("must return value from friendly field name: Federal Agency") {
    forAll(tsGen) { (ts: TransmittalSheet) =>
      ts.valueOf("Federal Agency") mustBe ts.agency.value
    }
  }

  property("must return value from friendly field name: Contact Email") {
    forAll(tsGen) { (ts: TransmittalSheet) =>
      ts.valueOf("Contact Person's E-mail Address") mustBe ts.contact.email
    }
  }

  property("must not error when field name doesn't match one in the list") {
    forAll(tsGen) { ts =>
      ts.valueOf("Nonsense Field") mustBe "error: field name mismatch"
    }
  }
}
