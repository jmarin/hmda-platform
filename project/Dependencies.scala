import sbt._

object Dependencies {

  lazy val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % "test"
  lazy val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck % "test"
  lazy val logback = "ch.qos.logback" % "logback-classic" % Version.logback
  lazy val akkaTyped = "com.typesafe.akka" %% "akka-typed" % Version.akka
  lazy val akkaCluster = "com.typesafe.akka" %% "akka-cluster" % Version.akka
  lazy val akkaClusterSharding = "com.typesafe.akka" %% "akka-cluster-sharding" % Version.akka
  lazy val akkaPersistence = "com.typesafe.akka" %% "akka-persistence" % Version.akka

}
