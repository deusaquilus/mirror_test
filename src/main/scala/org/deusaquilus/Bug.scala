package org.deusaquilus

import scala.deriving._

import scala.quoted._
import scala.quoted.matching._
import scala.compiletime.{erasedValue, summonFrom}
import MyTypeclass.{given, _}

case class Person(name:String)

trait MyTypeclass[T] {
  def useit(elem: T): String
}

object MyTypeclass {
  inline def derived[T](implicit ev: Mirror.Of[T]): MyTypeclass[T] = new MyTypeclass[T] {
    def useit(value: T): String =
      inline ev match {
        case m: Mirror.SumOf[T] => "it's a sum, yay!"
        case m: Mirror.ProductOf[T] => "it's a product, yay!"
      }
  }
}

object Helper {
  inline def findAndEncode[T](value: =>T): String = ${ findAndEncode('value) }

  def findAndEncode[T](value: Expr[T])(given qctx: QuoteContext, t: Type[T]): Expr[String] = {
    import qctx.tasty.{_, given}

    val mirrorTpe = '[deriving.Mirror.Of[$t]]
    val mirrorExpr = summonExpr(given mirrorTpe) match {
      case Some(mirror) => mirror
    }

    '{
      given MyTypeclass[T] = MyTypeclass.derived($mirrorExpr)
      val mytc = summon[MyTypeclass[$t]]
      mytc.useit($value)
    }
  }
}