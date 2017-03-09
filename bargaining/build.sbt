
lazy val commonSettings = Seq(
  organization := "com.codespace",
  scalaVersion := "2.12.1"
)


lazy val commons = (project in file("commons"))
                   .settings(commonSettings)
                   .settings(name := "commons")

lazy val agents = (project in file("agents"))
                   .settings(commonSettings)
                   .settings(name := "agents")
                   .dependsOn(commons)

lazy val gameRunner = (project in file("gameRunner"))
                   .settings(commonSettings)
                   .settings(name := "gameRunner")
                   .dependsOn(commons)

lazy val root = (project in file(".")).aggregate(commons,agents, gameRunner)


