package example

sealed trait HList
{
  def ::[A](a:A) = HCons(a,this)
}

case object HNil extends HList

case class HCons[A,B <: HList](head:A,tail:B) extends HList {

  type E = A

}


