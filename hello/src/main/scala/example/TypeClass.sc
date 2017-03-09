
trait MyOrdering[T]
{

  def compare(x:T,y:T):Int

}

implicit object IntCompare extends MyOrdering[Int] {
  override def compare(x: Int, y: Int): Int =
    x - y
}

implicit object StringCompare extends MyOrdering[String] {
  override def compare(x: String, y: String): Int =
    x.compare(y)
}

implicit def  toPairCompare[A,B](implicit aOrd:MyOrdering[A], bOrd:MyOrdering[B]): MyOrdering[(A,B)]  = new MyOrdering[(A,B)] {
  override def compare(x: (A, B), y: (A, B)): Int = {
    val cA = aOrd.compare(x._1,y._1)
    if (cA != 0) cA else bOrd.compare(x._2,y._2)
  }
}


def sort[A](x:List[A])(implicit ord:MyOrdering[A]):List[A] = ???


sort(List(1,2,3,4,5))
sort(List("1","2"))

sort(List((1,"2"),(2,"3")))