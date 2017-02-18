import example._

trait A
{

  def a:String = "A"

}

trait B
{

  thisB =>

  val a:String =
     "ab" //super.a + "B"

  def b():String = "B"

}

trait C extends A with B
{

  self =>

  override val a: String = "ac"


}

class D(val x:String, val y:Int) extends C with A
{

  def this(x:Int)=
  {  this(x.toString,x)  }

  def copy(x:String=this.x, y:Int = this.y) = new D(x,y)

}

sealed trait MyOption[+X]
case class MySome[X](x:X) extends MyOption[X]
case object MyNone extends MyOption[Nothing]


object D extends Function2[String,Int,D] {
  def apply(s:String, x: Int):D = new D(s,x)

  def unapply(arg: D): Option[(String,Int)] =
    Some((arg.x,1))



}

val d = D("AAA",0)
d match {
  case D(x,y) => (x,y)
}

//Point:Function2[Int,Int,Point]

val p1 = Point(1,2)
val p2 = Point(1,2)

p1 == p2


p1 match {
  case Point(x,2) => s"My, x = ${x}"
  case _ => "Not my"
}

val u = ()
val nil = Nil

def myFun[X](l:MyList[X]):Int =
{
  l match {
    case MyCons(a,MyCons(b,c)) =>
          val tail =myFun(MyCons(b,c)); tail + 3
    case MyCons(a,b) if (a==b) => 1
    case MyNil  => 0
  }
}

