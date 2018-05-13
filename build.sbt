import Dependencies._

name := """poc-play"""
organization := "com.mrgueritte"

version := "1.0-SNAPSHOT"

scalaVersion := "2.12.3"

lazy val root =
  (project in file(".")).enablePlugins(PlayScala)

routesImport ++= "utils.RoutesParser._" :: "utils.CatId" ::  Nil

libraryDependencies ++=  Librairies.scalaTest :: Librairies.slick :: Librairies.enumeratum ::
  Librairies.database :: Librairies.macwireMacro :: Librairies.slickEvolution :: Nil

libraryDependencies ++= Librairies.jto ++ Librairies.macwire

resolvers += Repositories.sonatypeOss

libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"