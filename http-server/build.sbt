name := "http-server"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-http" % "10.0.5",
  "com.typesafe.akka" %% "akka-http-core" % "10.0.5",
  "org.json4s" %% "json4s-native" % "3.5.0",
  "org.json4s" %% "json4s-ext" % "3.5.0",
  "de.heikoseeberger" %% "akka-http-json4s" % "1.14.0"
)