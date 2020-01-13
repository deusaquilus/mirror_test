package org.deusaquilus

import SummonJsonEncoderTest._

object Test {
  val stuff = PersonSimple("Joe", 123)

  def main(args: Array[String]):Unit = {
    println(SummonJsonEncoderTest.encodeAndMessAroundType(stuff) )
  }
}
