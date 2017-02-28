package example

import scala.annotation.tailrec

//  A <: B  =>  List[A] <: List[B]

// Any x:   Nothing <: X

//

// covariance:      X <: Y => F[X] <: F[Y]
// contravariance:  X <: Y => F[X] >: F[Y]
//  inv = cov + contr:  X != Y => not (F[X] >: F[Y], F[X] <: F[Y])
sealed trait MyList[+X] {

  thisList =>

  def isEmpty(): Boolean



  def ::[Y >: X](x: Y): MyList[Y] = MyCons(x, this)

  class MyListWithFilter(p: X => Boolean)
  {

    def map[Y](f: X => Y): MyList[Y]  =
    {
      thisList.filter(p).map(f)
    }

    def filter(p1: X => Boolean) =
      new MyListWithFilter(x => p(x) && p1(x))

  }

  // for(x <- l if p) yield f(x)
  // l.withFilter(p).map(x => f(x))

  def withFilter(predicate: X => Boolean) =
     new MyListWithFilter(predicate)

  def filter(predicate: X => Boolean): MyList[X] =
    this match {
      case MyCons(h, tail) => if (predicate(h))
        MyCons(h, tail.filter(predicate))
      else
        tail.filter(predicate)
      case MyNil => MyNil
    }

  def tail: MyList[X]

  //@tailrec
  //TODO: think about @tailrec
  def map[Y](f: X => Y): MyList[Y] = {
    this match {
      case MyCons(h, t) => f(h) :: t.map(f)
      case MyNil => MyNil
    }
  }


  def flatMap[Y](f: X => MyList[Y]): MyList[Y] =
  {

    //TODO:
    def flatMapT(f: X=>MyList[Y], restIn: MyList[X], out: MyList[Y]): MyList[Y] = {
      restIn match {
        case MyNil => MyNil
        case MyCons(h, t) =>
          val withH = out.appendList(f(h))
          withH.appendList(flatMapT(f,t,out))
      }
    }

    flatMapT(f,this,MyNil)
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

  def appendList[Y >: X](x: MyList[Y]): MyList[Y] =
  {
    this match {
      case MyNil => x
        // BAD !!!!
        //TODO implement effective
      case MyCons(h,t) => append(h).appendList(t)
    }
  }

  // (l) <:  (l append x)
  // tail(l) <: l


  def reverse(): MyList[X] = {
    def rev1(result: MyList[X], remain: MyList[X]): MyList[X] = {
      remain match {
        case MyNil => result
        case MyCons(h, t) => rev1(h :: result, t)
      }
    }
    rev1(MyNil, this)
  }



  def doWhile(p: Unit => Boolean)(f: X=>Unit): Unit =
  {
    this match {
      case MyCons(h,t) => if (p(h)) {
                             f(h)
                             t.doWhile(p)(f)
                          } else MyNil
      case MyNil => MyNil
    }
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
  def dropWhile[T](xs: MyList[T])(p: T => Boolean): MyList[T] = {
    xs match {
      case MyCons(head,tail) if p(head) => dropWhile(tail)( p)
      case _ => xs
    }
  }


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




