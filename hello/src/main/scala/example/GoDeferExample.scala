package example

class Scope {

  private var effects: Array[() => Unit] = Array()

  def runEffects(): Unit = {
    effects.foreach((f) => {
      try {
        f()
      } catch {
        case e: Exception => e.printStackTrace()
      }
    })
  }

  def deferr(block: => Unit): Unit = {
    this.effects = effects ++ Array(block _)
  }
}

object Scoped {

  def apply[A](f: Scope => A): A = {
    val scope = new Scope
    val result = try {
      f(scope)
    } finally {
      scope.runEffects()
    }

    result
  }
}
