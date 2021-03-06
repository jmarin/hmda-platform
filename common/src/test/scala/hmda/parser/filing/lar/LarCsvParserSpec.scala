package hmda.parser.filing.lar

import hmda.model.filing.lar.LarGenerators._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, PropSpec}

class LarCsvParserSpec extends PropSpec with PropertyChecks with MustMatchers {

  property("Loan Application Register CSV Parser must parse values into LAR") {
    forAll(larGen) { lar =>
      val csv = lar.toCSV
      LarCsvParser(csv) mustBe Right(lar)
    }
  }
}
