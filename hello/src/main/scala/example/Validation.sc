import scalaz._
import Scalaz._
import scalaz.syntax.validation._
import scalaz.Failure

val f1: ValidationNel[String, Int] = Failure(NonEmptyList("Failure1"))
val f2: ValidationNel[String, Int] = Failure(NonEmptyList("Failure2"))

(f1 |@| f2) apply {_ + _}

List(f1,f2)