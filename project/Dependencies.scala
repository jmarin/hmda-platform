import sbt._

object Dependencies {

  lazy val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % "test"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck % "test"
  lazy val logback = "ch.qos.logback" % "logback-classic" % Version.logback
  val akkaSlf4J = "com.typesafe.akka" %% "akka-slf4j" % Version.akka
  lazy val akkaTyped = "com.typesafe.akka" %% "akka-typed" % Version.akka
  lazy val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % Version.akka
  lazy val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding" % Version.akka
  lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % Version.akka
  lazy val akkaManagementClusterBootstrap = "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % Version.akkaClusterManagement
  lazy val akkaServiceDiscoveryDNS = "com.lightbend.akka.discovery" %% "akka-discovery-dns" % Version.akkaClusterManagement
  lazy val akkaManagement = "com.lightbend.akka.management" %% "akka-management" % Version.akkaClusterManagement
  lazy val akkaClusterHttpManagement = "com.lightbend.akka.management" %% "akka-management-cluster-http" % Version.akkaClusterManagement

}
