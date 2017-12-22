import Dependencies._
import BuildSettings._

lazy val hmda = (project in file("."))
  .settings(hmdaBuildSettings:_*)
  .settings(
    Seq(
      libraryDependencies ++= Seq(
        scalaTest,
        scalaCheck,
        logback
      )
    )
  )

lazy val model = (project in file("model"))
    .settings(hmdaBuildSettings:_*)

