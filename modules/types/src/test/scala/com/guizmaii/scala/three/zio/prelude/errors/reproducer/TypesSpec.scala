package com.guizmaii.scala.three.zio.prelude.errors.reproducer

import zio.Scope
import zio.test.*
import zio.test.Assertion.*

object TypesSpec extends ZIOSpecDefault {
  override def spec: Spec[TestEnvironment & Scope, Any] =
    suite("types")(
      test("NonEmptyString macro should compile") {
        assertZIO(
          typeCheck("""com.guizmaii.scala.three.zio.prelude.errors.reproducer.types.NonEmptyString("toto")""")
        )(isRight)
      },
    )
}
