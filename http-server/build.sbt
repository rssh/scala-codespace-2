name := "http-server"

version := "1.0"

scalaVersion := "2.12.1"

val akkaHttpVersion = "10.0.5"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.json4s" %% "json4s-ext" % "3.5.0",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.14.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test
)