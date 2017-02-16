import example.Hello

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

// val fib1 = new Function1{
//    def apply(x:Int)=fib(x)
//  }

// maxF(from:Int, to:Int, f:Int=>Int):Int = {
//
//}

fib(3)