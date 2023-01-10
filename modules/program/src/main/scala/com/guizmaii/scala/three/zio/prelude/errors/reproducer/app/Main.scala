package com.guizmaii.scala.three.zio.prelude.errors.reproducer.app

import com.guizmaii.scala.three.zio.prelude.errors.reproducer.types.NonEmptyString

object Main extends App {

  private val toto = NonEmptyString("toto")

  print(toto)

}
