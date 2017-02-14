package example


object Hello extends Greeting with App {
  println(greeting)
}

object Hello1 extends Greeting
{

   // void main(String[] args) { .. }
  def main(args: Array[String]): Unit = {
    println(greeting)
  }


}


trait Greeting {
  // class GreetingHolder
  // { static String greeting = "hello"; }
  lazy val greeting: String = "hello"
}
