package org.deusaquilus

import scala.deriving._
import scala.quoted._
import scala.quoted.matching._
import scala.compiletime.{erasedValue, summonFrom}
import JsonEncoder.{given, _}

object SummonJsonEncoderTest {

  inline def encodeAndMessAroundType[T](value: =>T): String = {
    summonFrom {
      case m: Mirror.ProductOf[T] => derived(m).encode(value)
    }
  }
}