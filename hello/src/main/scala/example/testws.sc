import example.{Hello, MyCons, MyList, MyNil}

import scala.annotation.tailrec

val x:Int = 1

var y:Int = 2

def f(x:Int):Int = x+1

val zv = (x,y,1)

//zv._1
//zv._2
//zv._3
{
  (x+1,
   y )
}


def fib(x:Int):Int =
{
  @tailrec
  def loop(c:Int,p:Int,n:Int):Int =
  {
    println(s"fib(${(c,p,n,x)})")
    if (n >= x) {
      c
    } else {
      loop(p+c,c,n+1)
    }
  }
  require(x >= 0)
  loop(1,1,1)
}

val fib1 = fib _

MyList(1,2,3)

val myArr = Array(1,2,3).toSeq

def fun(x: Int*): String = {
  x match {
    case head :: tail => "Ok"
    case _ => "not ok"
  }
}

fun(1,2,3)

MyCons(1, MyNil)

1::(2::(3::(4::MyNil)))



MyCons(1,MyNil) match {
  case 1 MyCons MyNil => "not ok"
  case MyNil => "A"
}



// val fib1 = new Function1{
//    def apply(x:Int)=fib(x)
//  }

// maxF(from:Int, to:Int, f:Int=>Int):Int = {
//
//}

fib(3)