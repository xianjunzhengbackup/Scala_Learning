ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.4"

lazy val root = (project in file("."))
  .settings(
    name := "Scala_Learning"
  )

resolvers +="Artima Maven Repository" at "https://repo.artima.com/releases"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4"