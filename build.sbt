import Dependencies._
import BuildSettings._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

lazy val commonDeps = Seq(logback, scalaTest, scalaCheck)

lazy val hmda = (project in file("."))
  .settings(hmdaBuildSettings: _*)
  .settings(
    Seq(
      libraryDependencies ++= Seq(
        scalaTest,
        scalaCheck,
        logback
      )
    ),
    scalafmtOnCompile in ThisBuild := true,
    scalafmtTestOnCompile in ThisBuild := true
  )
  .aggregate(model)

lazy val model = (project in file("model"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps
  )
