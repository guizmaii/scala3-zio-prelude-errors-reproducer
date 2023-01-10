package com.guizmaii.scala.three.zio.prelude.errors.reproducer

import zio.prelude.Assertion.notEqualTo
import zio.prelude.Subtype

@SuppressWarnings(Array("scalafix:ExplicitResultTypes"))
object types {

  object NonEmptyString extends Subtype[String] {
    // noinspection TypeAnnotation
    override def assertion = assert(notEqualTo(""))
  }
  type NonEmptyString = NonEmptyString.Type

}
