ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.guizmaii.scala.three.zio.prelude.errrors.reproducer"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "scala3-zio-prelude-errors-reproducer",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.5",
      "dev.zio" %% "zio-test" % "2.0.5" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
