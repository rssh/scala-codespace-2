// Structured typing

// Structured typing as wrapper on runtime reflect
def doAndClose(in: { def close():Unit })(
              f: =>Unit
                ) = {
  f
  in.close()
}

class MyCloseable {

  def close(): Unit = {
    Console.println("close")
  }

}

val obj = new MyCloseable()


doAndClose(obj){println("A")}


// Structured typign for type providers.
//(in compile)
def myObject() = new {
  def firstName = "A"
  def lastName = "B"
}

// see - firstName is aviable in IDE context=menu
myObject().firstName

class A1
{
  type B
}

val a1 = new A1 { def x:Int = 3 }

val a2 = new A1 {  type B = Int }
val a3 = new A1 {  type B = String }

