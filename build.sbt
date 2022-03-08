name := "OddDemo"

version := "0.1"

scalaVersion := "2.13.6"

val AkkaVersion = "2.6.18"
val AkkaHttpVersion = "10.2.9"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "org.squeryl" %% "squeryl" % "0.9.17",
  "com.auth0" % "java-jwt" % "3.18.3",
  "mysql" % "mysql-connector-java" % "8.0.25"
)

