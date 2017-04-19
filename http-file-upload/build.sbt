lazy val commonSettings = Seq(
  organization := "com.example",
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq("-unchecked","-deprecation", "-feature"
                         /* ,  "-Ymacro-debug-lite"  */
                         /*,   "-Ydebug"  ,  "-Ylog:lambdalift"  */
                     ),
  libraryDependencies ++= Seq (
    scalaVersion( "org.scala-lang" % "scala-reflect" % _ ).value,
    "org.scalatest" %% "scalatest" % "3.0.1" % Test,
    "org.typelevel" %% "cats" % "0.9.0",
    "com.chuusai" %% "shapeless" % "2.3.2"

  )
)

lazy val commonModel = (project in file("common-model")).settings(commonSettings: _*)

lazy val server = (project in file("server")).
  settings(commonSettings: _*).
  dependsOn(commonModel).
  settings(
    name := "upload-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.4.17",
      "com.typesafe.akka" %% "akka-http" % "10.0.5",
      "com.typesafe.akka" %% "akka-testkit" % "2.4.17" % "test",
      "com.typesafe.akka" %% "akka-stream" % "2.4.17",
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.17" % "test",
      "com.typesafe.akka" %% "akka-persistence" % "2.4.17",
      "com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.17",
       "io.circe" %% "circe-generic" % "0.7.0",
       "io.circe" %% "circe-parser" % "0.7.0",
       "de.heikoseeberger" %% "akka-http-circe" % "1.15.0"
    )
)

lazy val client = (project in file("client")).settings(commonSettings: _*)
    .dependsOn(commonModel)
    .settings(
     name := "upload-client",
     libraryDependencies ++= Seq(
       "com.typesafe.akka" %% "akka-http" % "10.0.5",
       "com.typesafe.akka" %% "akka-stream" % "2.4.17",
       "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.17" % "test",
       "io.circe" %% "circe-generic" % "0.7.0",
       "io.circe" %% "circe-parser" % "0.7.0",
       "de.heikoseeberger" %% "akka-http-circe" % "1.15.0"
     )
    )

lazy val root = Project(id = "upload", base = file("."), aggregate =
  Seq(commonModel,client,server))



