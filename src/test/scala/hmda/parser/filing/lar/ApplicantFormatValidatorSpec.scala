package hmda.parser.filing.lar

import org.scalatest.{MustMatchers, PropSpec}
import org.scalatest.prop.PropertyChecks
import hmda.model.filing.lar.LarGenerators._
import LarValidationUtils._
import ApplicantFormatValidator._
import cats.data.NonEmptyList
import cats.data.Validated.Invalid
import hmda.parser.filing.lar.LarParserErrorModel.InvalidEthnicity

class ApplicantFormatValidatorSpec
    extends PropSpec
    with PropertyChecks
    with MustMatchers {

  property(
    "Applicant must report Invalid Ethnicity for non numeric ethnicity field") {
    forAll(larGen) { lar =>
      val applicant = lar.applicant
      val badValues = extractValues(applicant).updated(0, "a")
      validateApplicant(
        ethnicity1 = badValues(0),
        ethnicity2 = badValues(1),
        ethnicity3 = badValues(2),
        ethnicity4 = badValues(3),
        ethnicity5 = badValues(4),
        otherHispanicOrLatino = badValues(5),
        ethnicityObserved = badValues(6),
        race1 = badValues(7),
        race2 = badValues(8),
        race3 = badValues(9),
        race4 = badValues(10),
        race5 = badValues(11),
        otherNative = badValues(12),
        otherAsian = badValues(13),
        otherPacific = badValues(14),
        raceObserved = badValues(15),
        sex = badValues(16),
        sexObserved = badValues(17),
        age = badValues(18),
        creditScore = badValues(19),
        creditScoreModel = badValues(20),
        otherCreditScore = badValues(21)
      ) mustBe Invalid(NonEmptyList.of(InvalidEthnicity))
    }
  }

}
