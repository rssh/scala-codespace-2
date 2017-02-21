package example

//  A <: B  =>  List[A] <: List[B]

// Any x:   Nothing <: X

//

// covariance:      X <: Y => F[X] <: F[Y]
// contravariance:  X <: Y => F[X] >: F[Y]
//  inv = cov + contr:  X != Y => not (F[X] >: F[Y], F[X] <: F[Y])
sealed trait MyList[+X] {


  def isEmpty(): Boolean



  def ::[Y >: X](x: Y): MyList[Y] = MyCons(x, this)

  def filter(predicate: X => Boolean): MyList[X] =
    this match {
      case MyCons(h, tail) => if (predicate(h))
        MyCons(h, tail.filter(predicate))
      else
        tail.filter(predicate)
      case MyNil => MyNil
    }

  def tail: MyList[X]


  def map[Y](f: X => Y): MyList[Y] = {
    this match {
      case MyCons(h, t) => f(h) :: t.map(f)
      case MyNil => MyNil
    }
  }



  def foldRight[S](z: S)(op: (X, S) => S): S =
    this match {
      case MyCons(h, t) => op(h, t.foldRight(z)(op))
      case MyNil => z
    }

  def append[Y >: X](x: Y): MyList[Y] = {
    if (x == MyNil) this
    else
      this match {
        case MyNil => x :: MyNil
        case MyCons(h, t) => h :: t.append(x)
      }
  }

  // (l) <:  (l append x)
  // tail(l) <: l


  // TODO: make effective

  def reverse(): MyList[X] = {
    def rev1(result: MyList[X], remain: MyList[X]): MyList[X] = {
      remain match {
        case MyNil => result
        case MyCons(h, t) => rev1(h :: result, t)
      }
    }
    rev1(MyNil, this)
  }

}

case class MyCons[X](head:X,tail:MyList[X]) extends MyList[X]
{

  override def isEmpty() = false


}

case object MyNil extends MyList[Nothing]
{

  override def isEmpty() = true

  override def tail = throw new IllegalArgumentException("dd")

}

object MyList
{

  def apply[T](xs: T* ): MyList[T] =
    {
      //val (a,b) = a->b
      println(xs.getClass)


      xs match {
        case +: (head, tail) => MyCons(head, apply(tail: _*) )
        case _ => MyNil
      }

      val xs1 = "t" +: xs

      val unapplyResult = +: . unapply(xs)
      if (unapplyResult.isDefined) {
        val Some((head, tail)) = unapplyResult
        MyCons(head,apply(tail: _*))
      } else {
        MyNil
      }


    }

}





// (1,2,3,4,5)
//  filter(x % 2 == 0)

// val x = (1::(2::(3::(4::5))))
// val y = x.tail



// 3::Nil  Nil.::(3)
//        X=Nothing  Y=Int  => List[Int]




