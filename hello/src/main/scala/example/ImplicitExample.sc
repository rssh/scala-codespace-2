
import example.MyList

import scala.annotation.{implicitAmbiguous, implicitNotFound}

val l = MyList(1,2,3)

def printList[A](l:List[A]) =
  l mkString ","

import example.Implicits._

printList(l)

implicit class StringWithTwice(val s:String)
{
  def twice()=s*2
}

implicit val x: Int = 4

def f(a:Int)(implicit adder:Int):Int =
 {
   a+adder
 }

f(3)(2)

//import scala.reflect.runtime.universe._


//def f[T](x:T)(implicit tt:TypeTag[T])

/*
implicit class StringWithTwice2(val s:String)
{
  def twice()=s*2
}
*/


//implicit def sTwice(s:String) = new StringWithTwice(s)


"111A".twice