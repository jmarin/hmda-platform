package hmda.model.filing

import org.scalacheck.Gen
import hmda.model.filing.FilingGenerators._
import hmda.model.census.Census._
import hmda.model.filing.ts.Address

object AddressGenerators {

  implicit val tsAddressGen: Gen[Address] = {
    for {
      street <- Gen.alphaStr
      city <- Gen.alphaStr
      state <- stateGen
      zipCode <- zipGen
    } yield Address(street, city, state, zipCode)
  }

  implicit def stateGen: Gen[String] = {
    Gen.oneOf(states.keys.toList)
  }

  implicit def zipGen: Gen[String] = {
    Gen.oneOf(zip5Gen, zipPlus4Gen)
  }

  implicit def zip5Gen: Gen[String] = stringOfN(5, Gen.numChar)

  implicit def zipPlus4Gen: Gen[String] = {
    for {
      zip <- zip5Gen
      plus <- stringOfN(4, Gen.numChar)
      sep = "-"
    } yield List(zip, plus).mkString(sep)
  }
}
