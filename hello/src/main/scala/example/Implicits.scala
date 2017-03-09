package example

import scala.annotation.tailrec

object Implicits {

  implicit def toList[A](x:MyList[A]):List[A]=
  {
    def loop(x:MyList[A]):List[A] =
    {
      x match {
        case MyNil => Nil
        case MyCons(h,t) => h :: loop(t)
      }
    }
    loop(x)
  }

}
