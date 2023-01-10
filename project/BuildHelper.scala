import sbt.Keys._
import sbt._

object BuildHelper {

  def commonSettings = {
    val specificOptions =
      List(
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, _)) => Seq(compilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"))
            case _            => List.empty
          }
        },
        scalacOptions ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, 12 | 13)) => Seq("-Ymacro-annotations", "-Xsource:3", "-P:kind-projector:underscore-placeholders")
            case Some((3, _))       => Seq("-source:future") // See https://github.com/oleg-py/better-monadic-for#note-on-scala-3
            case _                  => Seq.empty
          }
        },
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, _)) => Seq(compilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full))
            case _            => Seq.empty[ModuleID]
          }
        }
      )

    val crossOptions = List(
      scalacOptions --= (if (insideCI.value) Nil else Seq("-Xfatal-warnings")), // enforced by the pre-push hook too
      testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
      (Test / parallelExecution) := true,
      (Test / fork)              := true
    )

    val targetOptions = List(
      javacOptions ++= Seq("--release", "17"),
      javaOptions ++= Seq("--add-exports=java.base/sun.security.jca=ALL-UNNAMED"),
      scalacOptions ++= {
        CrossVersion.partialVersion(scalaVersion.value) match {
          case Some((2, _)) => Seq(s"-target:17")
          case Some((3, _)) => Seq(s"-release:17")
          case _            => Seq()
        }
      }
    )

    crossOptions ++ specificOptions ++ targetOptions ++ crossProjectSettings ++ noDoc
  }

  lazy val noDoc = Seq(
    (Compile / doc / sources)                := Seq.empty,
    (Compile / packageDoc / publishArtifact) := false
  )

  /**
   * Copied from Cats
   */
  lazy val noPublishSettings = Seq(
    publish         := {},
    publishLocal    := {},
    publishM2       := {},
    publishArtifact := false,
  )

  /**
   * Adapted from ZIO
   *
   * Allows you to put:
   *   - Scala2 specific code in `src/[main|test]/scala-2` directory
   *   - Scala3 specific code in `src/[main|test]/scala-3` directory
   *   - Shared code between Scala2 and Scala3 code in `src/[main|test]/scala` directory
   *
   * Enabled by defaults for all modules using the [[commonSettings]].
   */
  lazy val crossProjectSettings = Seq(
    Compile / unmanagedSourceDirectories ++= {
      crossPlatformSources(
        scalaVersion.value,
        "main",
        baseDirectory.value
      )
    },
    Test / unmanagedSourceDirectories ++= {
      crossPlatformSources(
        scalaVersion.value,
        "test",
        baseDirectory.value
      )
    }
  )

  /**
   * Adapted from ZIO
   */
  private def crossPlatformSources(scalaVer: String, conf: String, baseDirectory: File) = {
    val versions =
      CrossVersion.partialVersion(scalaVer) match {
        case Some((2, _)) => List("2")
        case Some((3, _)) => List("3")
        case _            => List.empty
      }

    val parentFile = baseDirectory.getParentFile

    for {
      version <- "scala" :: versions.map("scala-" + _)
      result   = parentFile / "src" / conf / version
      if result.exists
    } yield result
  }

}
