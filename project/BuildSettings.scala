import sbt.Keys._
import sbt._

object BuildSettings {
  val buildOrganization = "cfpb"
  val buildVersion      = "2.0.0"
  val buildScalaVersion = "2.12.4"

  val hmdaBuildSettings = Defaults.coreDefaultSettings ++
    Seq(
      organization := buildOrganization,
      version      := buildVersion,
      scalaVersion := buildScalaVersion,
      scalacOptions ++= Seq(
        "-Xlint",
        "-deprecation",
        "-unchecked",
        "-feature"),
      parallelExecution in Test := true,
      testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-oC")
    )

}