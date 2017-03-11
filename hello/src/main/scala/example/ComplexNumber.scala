package example

import scala.language.implicitConversions

object I {
  def *(b: Int): ComplexNumber =
    ComplexNumber(0, b)
}

case class ComplexNumber(val a: Int, b: Int) {

  def mod(): Double =
    Math.sqrt(a * a + b * b)
}

object ComplexNumeric extends Fractional[ComplexNumber] {

  override def plus(x: ComplexNumber, y: ComplexNumber): ComplexNumber =
    ComplexNumber(x.a + y.a, x.b + y.b)

  override def minus(x: ComplexNumber, y: ComplexNumber): ComplexNumber =
    ComplexNumber(x.a - y.a, x.b - y.b)

  override def times(x: ComplexNumber, y: ComplexNumber): ComplexNumber =
    ComplexNumber((x.a * y.a) - (x.b * y.b), (x.a * y.b) + (x.b * y.a))

  override def div(x: ComplexNumber, y: ComplexNumber): ComplexNumber =
    ComplexNumber(
      (x.a * y.a + x.b * y.b) / (y.a * y.a + y.b * y.b),
      (x.b * y.a - x.a * y.b) / (y.a * y.a + y.b * y.b))

  override def negate(x: ComplexNumber): ComplexNumber =
    minus(ComplexNumber(0, 0), x)

  override def fromInt(x: Int): ComplexNumber =
    ComplexNumber(x, 0)

  override def toInt(x: ComplexNumber): Int =
    x.a

  override def toLong(x: ComplexNumber): Long =
    x.a.toLong

  override def toFloat(x: ComplexNumber): Float =
    x.a.toFloat

  override def toDouble(x: ComplexNumber): Double =
    x.a.toDouble

  override def compare(x: ComplexNumber, y: ComplexNumber): Int =
    x match {
      case ComplexNumber(y.a, y.b) => 0
      case _ => 1
    }
}

object ImplicitHolder {

  implicit def mkNumericOps(x: ComplexNumber): ComplexNumeric.Ops =
    ComplexNumeric.mkNumericOps(x)

  implicit def int2ComplexOps(x: Int): ComplexNumeric.Ops =
    mkNumericOps(x)
  //mkNumericOps(ComplexNumber(x,0))

  implicit def int2Complex(x: Int): ComplexNumber =
    ComplexNumber(x, 0)

  // Ops:  + (rhs:T):T
}
