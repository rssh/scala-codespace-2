package example



class Opt[T<:AnyRef](val x:T) extends AnyVal
{
  def isDefined() : Boolean = (x eq null)
  def get(): T = x
}


case class MyPair[A,B](a:A@specialized(Int,Double),
                       b:B@specialized(Int,Double))



object ShortWord
{

  def unapply(v:String): Opt[String] =
    new Opt(if (v.size < 5) v else null)

}