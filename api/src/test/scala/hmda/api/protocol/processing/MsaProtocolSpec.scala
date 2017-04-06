package hmda.api.protocol.processing

import hmda.api.model.{ IrsResponse, ModelGenerators }
import hmda.query.model.filing.{ Msa, MsaSummary }
import org.scalatest.{ MustMatchers, PropSpec }
import org.scalatest.prop.PropertyChecks
import spray.json.{ JsArray, JsNumber, JsObject, JsString }

class MsaProtocolSpec extends PropSpec with PropertyChecks with MustMatchers with ModelGenerators with MsaProtocol {

  val msa1 = Msa("second", 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
  val expectedMsa1 = JsObject(
    ("id", JsString("second")),
    ("totalLars", JsNumber(12)),
    ("totalAmount", JsNumber(11)),
    ("conv", JsNumber(10)),
    ("FHA", JsNumber(9)),
    ("VA", JsNumber(8)),
    ("FSA", JsNumber(7)),
    ("oneToFourFamily", JsNumber(6)),
    ("MFD", JsNumber(5)),
    ("multiFamily", JsNumber(4)),
    ("homePurchase", JsNumber(3)),
    ("homeImprovement", JsNumber(2)),
    ("refinance", JsNumber(1))
  )
  /*
  property("Msa must have correct JSON format") {
    msa1.toJson mustBe expectedMsa1
  }
  */

  val summ = MsaSummary(55, 54, 53, 52, 51, 59, 58, 57, 56, 55, 54, 53)
  val expectedSummary = JsObject(
    ("lars", JsNumber(55)),
    ("amount", JsNumber(54)),
    ("conv", JsNumber(53)),
    ("FHA", JsNumber(52)),
    ("VA", JsNumber(51)),
    ("FSA", JsNumber(59)),
    ("oneToFourFamily", JsNumber(58)),
    ("MFD", JsNumber(57)),
    ("multiFamily", JsNumber(56)),
    ("homePurchase", JsNumber(55)),
    ("homeImprovement", JsNumber(54)),
    ("refinance", JsNumber(53))
  )

  /*
  property("MsaSummary must have correct json format") {
    summ.toJson mustBe expectedSummary
  }
  */

  val irs = IrsResponse(List(msa1), summ, "uri/path/", 4, 315)

  property("IrsResponse must have correct JSON format") {
    IrsResponseJsonFormat.write(irs) mustBe JsObject(
      ("msas", JsArray(expectedMsa1)),
      ("summary", expectedSummary),
      ("count", JsNumber(20)),
      ("total", JsNumber(315)),
      ("_links", JsObject(
        ("href", JsString("uri/path/{rel}")),
        ("self", JsString("?page=4")),
        ("first", JsString("?page=1")),
        ("prev", JsString("?page=3")),
        ("next", JsString("?page=5")),
        ("last", JsString("?page=16"))
      ))
    )
  }

}
