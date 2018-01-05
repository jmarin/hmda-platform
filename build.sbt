import Dependencies._
import BuildSettings._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

lazy val commonDeps = Seq(logback, scalaTest, scalaCheck)

lazy val hmda = (project in file("."))
  .enablePlugins(JavaServerAppPackaging, DockerPlugin, AshScriptPlugin)
  .settings(hmdaBuildSettings: _*)
  .settings(
    Seq(
      mainClass in Compile := Some("hmda.model.Main"),
      assemblyJarName in assembly := {
        s"${name.value}2.jar"
      }
    ),
    scalafmtOnCompile in ThisBuild := true,
    scalafmtTestOnCompile in ThisBuild := true,
    // removes all jar mappings in universal and appends the fat jar
    mappings in Universal := {
      // universalMappings: Seq[(File,String)]
      val universalMappings = (mappings in Universal).value
      val fatJar = (assembly in Compile).value
      // removing means filtering
      val filtered = universalMappings filter {
        case (_, fileName) => !fileName.endsWith(".jar")
      }
      // add the fat jar
      filtered :+ (fatJar -> ("lib/" + fatJar.getName))
    },
    //dockerBaseImage := "openjdk:9.0.1-11-jre-slim",
    dockerBaseImage := "openjdk:8-jre-alpine",
    // the bash scripts classpath only needs the fat jar
    scriptClasspath := Seq((assemblyJarName in assembly).value)
  )
  .dependsOn(model)

lazy val model = (project in file("model"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps
  )
