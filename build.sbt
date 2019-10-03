name := "Akka Stream Framing"

version := "0.1-SNAPSHOT"

scalaVersion := "2.13.1"


lazy val akkaVersion = "2.5.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)

javacOptions += "-Xlint:deprecation"

scalacOptions += "-deprecation"

