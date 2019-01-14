package hmda.api.http.codec.institution

import hmda.model.institution.Institution
import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.institution.InstitutionGenerators._
import io.circe.syntax._
import hmda.api.http.codec.institution.InstitutionCodec._

class InstitutionCodecSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  property("Institution must encode/decode to/from JSON") {
    forAll(institutionGen) { institution =>
      val json = institution.asJson
      json.as[Institution].getOrElse(Institution.empty) mustBe institution
    }
  }

}
