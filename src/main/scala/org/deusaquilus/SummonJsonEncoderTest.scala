package org.deusaquilus

import scala.deriving._
import scala.quoted._
import scala.quoted.matching._
import scala.compiletime.{erasedValue, summonFrom}
import JsonEncoder.{given, _}

object SummonJsonEncoderTest {

  inline def encodeAndMessAroundType[T](value: =>T): String = ${ encodeAndMessAroundTypeImpl('value) }

  def encodeAndMessAroundTypeImpl[T](value: Expr[T])(given qctx: QuoteContext, t: Type[T]): Expr[String] = {
    import qctx.tasty.{_, given}

    val mirrorTpe = '[Mirror.Of[$t]]
    val mirrorExpr = summonExpr(given mirrorTpe) match {
      case Some(mirror) => mirror
    }

    '{
      given JsonEncoder[T] = JsonEncoder.derived($mirrorExpr)
      val encoder = summon[JsonEncoder[$t]]
      encoder.encode($value)
    }
  }
}