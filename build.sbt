import Dependencies._
import BuildSettings._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import com.typesafe.sbt.packager.docker._

lazy val commonDeps = Seq(logback, scalaTest, scalaCheck)
lazy val akkaDeps = Seq(
  akkaSlf4J,
  akkaCluster,
  akkaTyped,
  akkaClusterTyped,
  akkaStream,
  akkaManagement,
  akkaManagementClusterBootstrap,
  akkaServiceDiscoveryDNS,
  akkaServiceDiscoveryKubernetes,
  akkaClusterHttpManagement
)
lazy val akkaPersistenceDeps = Seq(akkaPersistence, akkaClusterSharding)
lazy val akkaHttpDeps = Seq(akkaHttp, akkaHttpTestkit, akkaHttpCirce)
lazy val circeDeps = Seq(circe, circeGeneric)

lazy val scalafmtSettings = Seq(
  scalafmtOnCompile in ThisBuild := true,
  scalafmtTestOnCompile in ThisBuild := true
)

lazy val dockerSettings = Seq(
  Docker / maintainer := "Juan Marin Otero",
  Docker / version := "latest",
  dockerBaseImage := "openjdk:10-jre-slim",
  dockerExposedPorts := Vector(8080, 8081, 8082, 19999),
  packageName := "hmda-platform",
  dockerRepository := Some("jmarin")
)

dockerEntrypoint ++= Seq(
  """-Dakka.remote.netty.tcp.hostname="$(eval "echo $AKKA_REMOTING_BIND_HOST")"""",
  """-Dakka.management.http.hostname="$(eval "echo $AKKA_REMOTING_BIND_HOST")""""
)

dockerCommands :=
  dockerCommands.value.flatMap {
    case ExecCmd("ENTRYPOINT", args @ _*) =>
      Seq(Cmd("ENTRYPOINT", args.mkString(" ")))
    case v => Seq(v)
  }

lazy val packageSettings = Seq(
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
  // the bash scripts classpath only needs the fat jar
  scriptClasspath := Seq((assemblyJarName in assembly).value),
  dependencyOverrides ++= akkaDeps ++ akkaPersistenceDeps ++ akkaHttpDeps
)

lazy val hmda = (project in file("."))
  .enablePlugins(JavaServerAppPackaging,
                 sbtdocker.DockerPlugin,
                 AshScriptPlugin)
  .settings(hmdaBuildSettings: _*)
  .settings(
    Seq(
      mainClass in Compile := Some("hmda.HmdaPlatform"),
      assemblyJarName in assembly := {
        s"${name.value}2.jar"
      }
    ),
    scalafmtSettings,
    dockerSettings,
    packageSettings,
    libraryDependencies ++= commonDeps ++ akkaDeps ++ akkaPersistenceDeps ++ akkaHttpDeps ++ circeDeps
  )
