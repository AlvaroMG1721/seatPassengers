name := "pruebaBoxever"
version := "0.1"
scalaVersion := "2.11.8"

assemblyJarName in assembly := "seatPassengers.jar"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.5" % "test"
)
