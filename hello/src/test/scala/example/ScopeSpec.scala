package example

import org.scalatest._

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
}
