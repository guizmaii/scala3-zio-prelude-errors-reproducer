import BuildHelper._
import Libraries._

lazy val scala213 = "2.13.8"
lazy val scala32  = "3.2.1"

val CrossScalaVersionCompile = List(scala213, scala32)

Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion     := scala213
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.guizmaii.scala.three.zio.prelude.errors.reproducer"
ThisBuild / organizationName := "guizmaii.corp"

// ### Modules ###

lazy val root =
  (project in file("."))
    .settings(noDoc: _*)
    .settings(
      name               := "scala3-zio-prelude-errors-reproducer",
      publish / skip     := true,
      // crossScalaVersions must be set to Nil on the aggregating project
      crossScalaVersions := Nil
    )
    .aggregate(types)
    .aggregate(program)

lazy val types =
  project
    .in(file("modules/types"))
    .settings(noDoc: _*)
    .settings(commonSettings: _*)
    .settings(crossScalaVersions := CrossScalaVersionCompile)
    .settings(libraryDependencies ++= Seq(zio, prelude) ++ zioTest)

lazy val program =
  project
    .in(file("modules/program"))
    .settings(noDoc: _*)
    .settings(commonSettings: _*)
    .settings(crossScalaVersions := CrossScalaVersionCompile)
    .settings(libraryDependencies ++= Seq(zio, prelude) ++ zioTest)
    .dependsOn(types)
