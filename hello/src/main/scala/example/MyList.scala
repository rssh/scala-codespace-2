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


  def map1[Y](f: X => Y): MyList[Y] = {
    @tailrec
    def forward(rest:MyList[X],processed:MyList[Y],f:X=>Y):MyList[Y]=
    {
      rest match {
        case MyNil => processed.reverse()
        case MyCons(h,t) => forward(t,f(h)::processed,f)
      }
    }
    forward(this,MyNil,f)
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

  //  (1,2) flatMap (x -> MyList(x,x)) => (1,1,2,2)
  def flatMap1[Y](f: X => MyList[Y]): MyList[Y] =
  {

    @tailrec
    def loop(rest:MyList[X],
             currentRest:MyList[Y],
             currentRev:MyList[Y],
             result: MyList[Y]):MyList[Y] =
    {
      currentRest match {
        case MyCons(h,t) => loop(rest,t,h::currentRev,result)
        case MyNil => currentRev match {
          case MyCons(h,t) => loop(rest,currentRest,t,h::result)
          case MyNil => rest match {
            case MyCons(h,t) => loop(t,f(h),MyNil,result)
            case MyNil => result
          }
        }
      }
    }

    loop(this,MyNil,MyNil,MyNil)

  }


  def foldRight[S](z: S)(op: (X, S) => S): S =
    this match {
      case MyCons(h, t) => op(h, t.foldRight(z)(op))
      case MyNil => z
    }

  def append[Y >: X](x: Y): MyList[Y] = {
      this match {
        case MyNil => x :: MyNil
        case MyCons(h, t) => h :: t.append(x)
      }
  }


  def appendList[Y >: X](x: MyList[Y]): MyList[Y] =
  {

    @tailrec
    def forward(revFrs:MyList[X],revFrsRest:MyList[X],snd:MyList[Y]):MyList[Y] =
      {
        //Nil, (1,2), (3,4,5)
        revFrsRest match {
          case MyNil => // (2,1) (3,4,5)
                        back(revFrs,snd)
          case MyCons(h,t) => forward(h::revFrs,t,snd)
        }

      }

    @tailrec
    def back(revFrs:MyList[X],snd:MyList[Y]):MyList[Y] =
    {
      // (2,1) (3,4,5)
      revFrs match {
        case MyNil => snd
        case MyCons(h,t) => // 3,4,5,2,1
                           back(t,h::snd)
      }
    }

    //TODO: It is possiblt to eliminate reverse ?
    forward(MyNil,this,x).reverse()

    /*
    // MyList(1,2).append(MyList(3,4,5)) = MyList(1,2,3,4,5)
    //
    *.
    this match {
      case MyNil => x
        // BAD !!!!
        //TODO implement effective
      case MyCons(h,t) => append(h).appendList(t)
      // n + (n+1) + (n+2) + ... (n+k) ~  O(n^2)
    }
    */

  }

  def prepend[Y>:X](x: Y): MyList[Y] =
  {
    x::this
  }


  // (l) <:  (l append x)
  // tail(l) <: l


  def reverse(): MyList[X] = {
    @tailrec
    def rev1(result: MyList[X], remain: MyList[X]): MyList[X] = {
      remain match {
        case MyNil => result
        case MyCons(h, t) => rev1(h :: result, t)
      }
    }
    rev1(MyNil, this)
  }



  def doWhile(p: X => Boolean)(f: X=>Unit): Unit =
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




