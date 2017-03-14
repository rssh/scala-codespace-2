package example

import org.scalatest._
import scala.util.Try

class ScopeSpec extends FlatSpec with Matchers {

  "Scope" should "defer computations" in {
    class GoDeferExample {
      var out: StringBuilder = new StringBuilder
      Scoped { scope =>
        out.append(1)
        scope.deferr {
          out.append(2)
        }
        out.append(3)
        scope.deferr {
          out.append(4)
        }
      }
    }

    val ex = new GoDeferExample
    assert("1324" == ex.out.toString())
  }

  "Scope" should "defer computations when exception" in {
      var out: StringBuilder = new StringBuilder
      val r = Try {
       Scoped { scope =>
        out.append("(")
        scope.deferr{ out.append(")") }
        throw new IllegalStateException("QQQ") 
        out.append("[")
        scope.deferr{ out.append("]") }
       }
      }
     assert(out.toString == "()" )
  }

}
