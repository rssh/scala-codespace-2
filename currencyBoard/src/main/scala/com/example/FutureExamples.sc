import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success, Try}

val f = Future{
  Thread.sleep(10*1000)
  throw new RuntimeException("Be-be-be!!")
  System.out.println("after sleep")
  10
}

f.onComplete{
  case Failure(ex) => ex.printStackTrace()
  case Success(v) => Console.println("received $v")
}

val fR = f.recover {
  case ex: IllegalArgumentException => 1
  case _ => 2
}


System.out.println("after f creation")

val f1 = f map ( _ + 1)

val f2 = f map ( _ + 2)

val x:Try[Int] = Try(Await.result(f1, 1.minute))