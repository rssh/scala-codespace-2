import example._
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

def doUntil(p: => Boolean)(f: =>Unit)=
{
  while(!p) f
}

def unless[A](p: => Boolean)(f: =>A)(g: =>A) = if (!p) f else g

class Deferred[U](x: =>U)
{
  def run():U = x
}

val r = new Deferred(
  System.out.println("Q")
)

r.run()
r.run()

def log(enabled: Boolean, message: =>String): Unit =
{
  if (enabled)
    Console.println(s"log:$message")
}

log(false, { System.out.print("AAA");"BBBB" })

def twice(enabled:Boolean, x: =>Int): Int =
{
  lazy val px = x
  if (enabled) {
    px + px
  } else 0
}