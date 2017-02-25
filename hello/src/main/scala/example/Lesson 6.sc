import java.util.NoSuchElementException

import example.{IntWrapper, Wrapper}

import scala.util.{Failure, Success, Try}
import scalaz.Validation

val matrix: List[List[Int]] = List(
   List(1,2,3),
   List(4,5,6),
   List(7,8,9))

var sum = 0

for { row <- matrix; cell <- row }  sum += cell

matrix.foreach{row => row.foreach{cell => sum += cell}}

sum

sum = 0

for { row <- matrix;
      cell <- row if cell % 2 == 0 }  sum += cell

matrix.foreach{row => row
  .withFilter(cell => cell % 2 == 0)
  .foreach{cell => sum += cell}}

val firstName = Some("Tim")
val middleName = Some("J.")
val lastName = Some("Pigden")

for {
   fn <- firstName
   mn <- middleName
   ln <- lastName
   delimiter = " "
} yield fn + delimiter + mn + delimiter + ln

firstName flatMap {fn =>
   middleName flatMap {mn =>
     (lastName.map{ln => (ln, " ")}
       map (pair => fn + pair._2 + mn + pair._2 + pair._1)
       )
   }
}

val optName:
  Option[(String, String, String)] =
  Option("Tim", "J.", "Pigden")

for {
  (fName, _, lName) <- optName
} yield fName + lName

/*
 M - monad
 M(a)


 */

//Option

val a = 1
val f: Int => Option[Int] = x => {
   if (x % 2 == 0) Some(x) else None
}

val g: Int => Option[Int] = x => {
  Some(x / 2)
}

// 1. Left Identity
// Option(a).flatMap(f) === f(a)

// 2. Right Identity
// val oA = Option(a)
// oA.flatMap(Option.apply) === oA

// 3. Associativity
/* Option(a).flatMap(f).flatMap(g) ===
   Option(a).flatMap(x => f(x).flatMap(g))
*/

Wrapper(1,2,3)

val fun = new Function1[Int,Int] {
  def apply(x: Int) = x*2
}

//fun(1,2,3) too many args

//IntWrapper(1,2,3)

Try{
  throw new NoSuchElementException("No data here")
}

/*
* File =>
* def checkSize(f: File): Option[File]
* def readValue(f: File): Option[Value]
* def transform(v: Value): Option[AnotherValue]
*
* def process(f: File) =
*   for {
*     file <- checkSize(f)
*     value <- readValue(file)
*     another <- transform(value)
*   } yield another
*
* Either[L, R]
*
* */

Try{4 / 0}
//def verify

// \/ [String \/ Int]
val failed = Left[String, Int]("Hi")

failed map (_ + 1)

val number = 0
val number2 = 2

def divide(x: Int, y: Int): Try[Int] = Try(x / y)
def sqrt(x: Int): Try[Double] = if (x < 0) {
  Failure(new IllegalArgumentException("can't get sqrt of a negative number"))
} else Success(math.sqrt(x))

def process(x: Int): Try[Double] = for {
  d <- divide(x,0)
  s <- sqrt(d)
} yield s

process(2).getOrElse(-1)
process(2) match {
  case Success(x) => x + 9

}



/*def processMap[K,V](map: Map[K, Seq[Option[V]]]) = {
  val
} */

