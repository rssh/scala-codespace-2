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

val fSuccess = Future successful 1

fSuccess.onComplete(System.out.println)