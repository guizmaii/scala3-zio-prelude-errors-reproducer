import sbt._

object Libraries {
  val zioVersion = "2.0.5"

  val prelude = "dev.zio" %% "zio-prelude" % "1.0.0-RC16"
  val zio     = "dev.zio" %% "zio"         % zioVersion

  val zioTest: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio-test"            % zioVersion,
    "dev.zio" %% "zio-test-sbt"        % zioVersion,
    "dev.zio" %% "zio-test-magnolia"   % zioVersion, // optional
    "dev.zio" %% "zio-test-scalacheck" % zioVersion,
    "dev.zio" %% "zio-mock"            % "1.0.0-RC9"
  )

}
