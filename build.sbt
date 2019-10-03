name := "Akka Stream Framing"

version := "0.1-SNAPSHOT"

scalaVersion := "2.13.1"


lazy val akkaVersion = "2.5.25"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
)

javacOptions += "-Xlint:deprecation"

scalacOptions += "-deprecation"

