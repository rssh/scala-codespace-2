

import java.sql.Timestamp
import java.util.concurrent.{Executors, TimeUnit}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Future, Promise}
import scala.util.Failure
import scala.concurrent.ExecutionContext.Implicits.global

val p = Promise[Int]()

val f = p.future

Future{
  Thread.sleep(1000)
  p.success(10)
}


def withTimeout[A](f:Future[A], timeout:FiniteDuration):Future[A] = {
  val scheduler = Executors.newSingleThreadScheduledExecutor()
  val promise = Promise[A]()
  scheduler.schedule(
    new Runnable() {
      override def run(): Unit = {
        if (!f.isCompleted) {
           promise.tryComplete(Failure(new RuntimeException("Timeout")))
        }
      }
    },
    timeout.toMillis,
    TimeUnit.MILLISECONDS
  )
  //promise.completeWith(f)

  f.onComplete{ r =>
    promise.tryComplete(r)
    scheduler.shutdownNow()
  }
  promise.future
}

/*
def dayFromNow():Timestamp =
{
  Thread.sleep(24*60*1000)
  new Timestamp(System.currentTimeMillis())
}
*/