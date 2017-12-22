package hmda.model.filing.ts

import org.scalacheck.Gen
import hmda.model.filing.FilingGenerators._
import hmda.model.filing.AddressGenerators._

object TsGenerators {

  implicit def tsGen: Gen[TransmittalSheet] = {
    for {
      institutionName <- Gen.alphaStr
      year <- activityYearGen
      quarter <- quarterGen
      contact <- contactGen
      totalLines <- totalLinesGen
      taxId <- taxIdGen
      agency <- agencyGen
      lei <- Gen.alphaStr

    } yield
      TransmittalSheet(
        1,
        institutionName,
        year,
        quarter,
        contact,
        agency,
        totalLines,
        taxId,
        lei
      )
  }

  implicit def contactGen: Gen[Contact] = {
    for {
      name <- Gen.alphaStr
      phone <- phoneGen
      email <- emailGen
      address <- tsAddressGen
    } yield Contact(name, phone, email, address)
  }

  implicit def totalLinesGen: Gen[Int] = {
    Gen.choose(0, 10000)
  }

  implicit def quarterGen: Gen[Int] = {
    Gen.oneOf(1, 2, 3, 4)
  }

  implicit def timeGen: Gen[Long] = {
    Gen.oneOf(201602021453L, 201602051234L)
  }

  implicit def activityYearGen: Gen[Int] = {
    Gen.oneOf(2018, 2019, 2020, 2021, 2022)
  }

}
