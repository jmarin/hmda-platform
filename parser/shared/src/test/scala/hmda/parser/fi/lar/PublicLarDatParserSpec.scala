package hmda.parser.fi.lar

import hmda.model.fi.lar.Loan
import org.scalatest.prop.PropertyChecks
import org.scalatest.{ MustMatchers, PropSpec }

class PublicLarDatParserSpec extends PropSpec with PropertyChecks with MustMatchers {

  val larStr = "20130000000324113200755310900420770014.0112005703  613225    5    NA   210000194\n" +
    "20130000000324111100370120764340297390.004500300   613457    8    NA   210000110"

  val lars = larStr.split("\n")

  property("Public LAR DAT file must parse") {
    val parsedLars = lars
      .map(str => PublicLarDatParser(str))

    parsedLars
      .map { lar =>
        lar.respondentId mustBe "0000000324"
        lar.agencyCode mustBe 1
      }

    val lar1 = parsedLars.head
    val lar2 = parsedLars.tail.head

    lar1
      .loan mustBe Loan("", "", 1, 0, 3, 2, 755)

    lar2
      .loan mustBe Loan("", "", 1, 0, 1, 1, 370)

  }

}
