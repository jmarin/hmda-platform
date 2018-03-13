package hmda.parser.filing.lar

import hmda.model.filing.lar._

object LarValidationUtils {

  def extractValues(lar: LoanApplicationRegister): Seq[String] = {

    List()
  }

  def extractValues(applicant: Applicant): Seq[String] = {
    val age = applicant.age.toString
    val creditScore = applicant.creditScore.toString
    val creditScoreType = applicant.creditScoreType.code.toString
    val otherCreditScore = applicant.otherCreditScoreModel

    extractEthnicityValues(applicant.ethnicity) ++
      extractRaceValues(applicant.race) ++
      extractSexValues(applicant.sex) ++
      List(age, creditScore, creditScoreType, otherCreditScore)

  }

  def extractEthnicityValues(ethnicity: Ethnicity): Seq[String] = {
    val eth1 = ethnicity.ethnicity1.code.toString
    val eth2 = ethnicity.ethnicity2.code.toString
    val eth3 = ethnicity.ethnicity3.code.toString
    val eth4 = ethnicity.ethnicity4.code.toString
    val eth5 = ethnicity.ethnicity5.code.toString
    val otherHispanicOrLatino = ethnicity.otherHispanicOrLatino
    val ethnicityObserved = ethnicity.ethnicityObserved.code.toString
    List(
      eth1,
      eth2,
      eth3,
      eth4,
      eth5,
      otherHispanicOrLatino,
      ethnicityObserved
    )
  }

  def extractRaceValues(race: Race): Seq[String] = {
    val race1 = race.race1.code.toString
    val race2 = race.race2.code.toString
    val race3 = race.race3.code.toString
    val race4 = race.race4.code.toString
    val race5 = race.race5.code.toString
    val otherNative = race.otherNativeRace
    val otherAsian = race.otherAsianRace
    val otherPacific = race.otherPacificIslanderRace
    val raceObserved = race.raceObserved.code.toString
    List(
      race1,
      race2,
      race3,
      race4,
      race5,
      otherNative,
      otherAsian,
      otherPacific,
      raceObserved
    )
  }

  def extractSexValues(sex: Sex): Seq[String] = {
    val sexStr = sex.sexEnum.code.toString
    val sexObserved = sex.sexObservedEnum.code.toString
    List(
      sexStr,
      sexObserved
    )
  }

}
