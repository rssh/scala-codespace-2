package example

// F-Boundedn polymorhism
//
trait Grouped[Self <: Grouped[Self]]
{

  this: Self =>

  //type Self = X


  def + (x:Self) = this.plus(x)

  def plus(x:Self):Self

  def zero:Self

}

case class Z10(val v:Int) extends Grouped[Z10]
{

  override def plus(x: Z10): Z10 = Z10((v + x.v)% 10)

  override def zero: Z10 = Z10(0)
}

/*
case class Z11(val v:Int) extends Grouped[Z11]
{
  override type Self = Z10

  override def plus(x: Z10): Z10 = Z10((v + x.v)% 10)

  override def zero: Z10 = Z10(0)
}
*/