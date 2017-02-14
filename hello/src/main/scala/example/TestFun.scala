package example


object TestFun {

  import scala.annotation.tailrec

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

  val fib1: (Int => Int) = fib
  val fib2 = fib _

}
