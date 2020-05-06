name := "hub-board-2"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",

  "com.lihaoyi" %% "cask" % "0.6.0",
  "com.lihaoyi" %% "ujson" % "1.0.0",
  
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
