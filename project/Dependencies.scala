import sbt._

object Dependencies {

  lazy val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % "test"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck % "test"
  lazy val logback = "ch.qos.logback" % "logback-classic" % Version.logback

}
