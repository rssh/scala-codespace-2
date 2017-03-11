package example

import java.io.FileInputStream

class Scope
{
  def deferr(block: =>Unit) = ???
}

object Scoped
{

  def apply[A](f: Scope=>A): A = ???

}

class GoDeferExample {



  Scoped{ scope =>
    val f1 = new FileInputStream("f1")
    scope.deferr{ f1.close() }
    val f2 = new FileInputStream("f2")
    scope.deferr{ f2.close() }
  }


}
