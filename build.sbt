import Dependencies._
import BuildSettings._
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._

lazy val commonDeps = Seq(logback, scalaTest, scalaCheck)
lazy val akkaDeps = Seq(akkaSlf4J,
                        akkaCluster,
                        akkaTyped,
                        akkaStream,
                        akkaManagement,
                        akkaManagementClusterBootstrap,
                        akkaServiceDiscoveryDNS,
                        akkaClusterHttpManagement)
lazy val akkaPersistenceDeps = Seq(akkaPersistence, akkaClusterSharding)
lazy val akkaHttpDeps = Seq(akkaHttp, akkaHttpCirce)

lazy val hmda = (project in file("."))
  .enablePlugins(JavaServerAppPackaging,
                 sbtdocker.DockerPlugin,
                 AshScriptPlugin)
  .settings(hmdaBuildSettings: _*)
  .settings(
    Seq(
      mainClass in Compile := Some("hmda.cluster.HmdaPlatform"),
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
    dockerUpdateLatest := true,
    dockerRepository := Some("jmarin"),
    dockerBaseImage := "openjdk:8-jre-alpine",
    // the bash scripts classpath only needs the fat jar
    scriptClasspath := Seq((assemblyJarName in assembly).value)
  )
  .dependsOn(cluster)
  .aggregate(
    model,
    parser,
    persistence,
    healthServer,
    healthClient,
    healthSharedJS,
    healthSharedJVM,
    api,
    cluster
  )

lazy val model = (project in file("model"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps
  )

lazy val parser = (project in file("parser"))
  .settings(hmdaBuildSettings: _*)
  .dependsOn(model)

lazy val persistence = (project in file("persistence"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps ++ akkaPersistenceDeps
  )
  .dependsOn(model)

lazy val validation = (project in file("validation"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps
  )
  .dependsOn(model)

lazy val query = (project in file("query"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps
  )
  .dependsOn(model)

lazy val publication = (project in file("publication"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps
  )
  .dependsOn(model)

lazy val healthServer = (project in file("health-server"))
  .enablePlugins(SbtWeb)
  .settings(hmdaBuildSettings: _*)
  .settings(
    scalaJSProjects := Seq(healthClient),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value,
    libraryDependencies ++= commonDeps ++ akkaDeps ++ akkaHttpDeps
  )
  .dependsOn(healthSharedJVM, model)

lazy val healthClient = (project in file("health-client"))
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .settings(hmdaBuildSettings: _*)
  .dependsOn(healthSharedJS)

lazy val healthShared =
  (crossProject.crossType(CrossType.Pure) in file("health-shared"))
    .settings(hmdaBuildSettings: _*)

lazy val healthSharedJS = healthShared.js

lazy val healthSharedJVM = healthShared.jvm

lazy val api = (project in file("api"))
  .settings(hmdaBuildSettings: _*)
  .settings(
    libraryDependencies ++= commonDeps ++ akkaDeps ++ akkaHttpDeps
  )
  .dependsOn(model)

lazy val cluster = (project in file("cluster"))
  .settings(hmdaBuildSettings: _*)
  .dependsOn(persistence)
  .dependsOn(validation)
  .dependsOn(query)
  .dependsOn(publication)
  .dependsOn(healthServer)
  .dependsOn(api)
