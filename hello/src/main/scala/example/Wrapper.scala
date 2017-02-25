package example

class Wrapper[T](val t: T) {
  def get = t
}

object Wrapper{
  def apply[T](t: T) = new Wrapper(t)
}

case class IntWrapper(i: Int)