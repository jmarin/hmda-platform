package hmda.model.filing

import hmda.model.institution._
import org.scalacheck.Gen

object FilingGenerators {

  implicit def agencyCodeGen: Gen[Int] = {
    agencyGen.map(_.value)
  }

  implicit def agencyGen: Gen[Agency] = {
    Gen.oneOf(OCC, FRS, FDIC, NCUA, HUD, CFPB)
  }

  implicit def taxIdGen: Gen[String] = {
    for {
      prefix <- stringOfN(2, Gen.numChar)
      sep = "-"
      suffix <- stringOfN(7, Gen.numChar)
    } yield List(prefix, suffix).mkString(sep)
  }

  implicit def phoneGen: Gen[String] = {
    for {
      p1 <- stringOfN(3, Gen.numChar)
      p2 <- stringOfN(3, Gen.numChar)
      p3 <- stringOfN(4, Gen.numChar)
      sep = "-"
    } yield List(p1, p2, p3).mkString(sep)
  }

  implicit def emailGen: Gen[String] = {
    for {
      name <- Gen.alphaStr.filter(s => s.nonEmpty)
      at = "@"
      domain <- Gen.alphaStr.filter(s => s.nonEmpty)
      dotCom = ".com"
    } yield List(name, at, domain, dotCom).mkString
  }

  // utility functions

  def stringOfN(n: Int, genOne: Gen[Char]): Gen[String] = {
    Gen.listOfN(n, genOne).map(_.mkString)
  }

  def stringOfUpToN(n: Int, genOne: Gen[Char]): Gen[String] = {
    val stringGen = Gen.listOf(genOne).map(_.mkString)
    Gen.resize(n, stringGen)
  }

  def stringOfOneToN(n: Int, genOne: Gen[Char]): Gen[String] = {
    val stringGen = Gen.nonEmptyListOf(genOne).map(_.mkString)
    Gen.resize(n, stringGen)
  }

}
