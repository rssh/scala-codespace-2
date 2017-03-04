
val x1: Option[_] = None
var x2: Option[_] = None
x2 = x1


trait Ap[F[_]]
{
  def bind[A,B](a:A)(f:A=>F[B]):F[B]
}

// type Lambda
class EitherAp[X] extends Ap[({type E[L]=Either[X,L]})#E]
{
  override def bind[A,B](a: A)(f: (A) => Either[X, B]) = f(a)
}

//def map2[A,C](f:Either[A, ?] =>C)
