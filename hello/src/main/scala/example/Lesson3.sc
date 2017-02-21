//can we write short partial functions where multi-argument function is expected?
def test(inner: Function2[Int,Int,Int]) = inner
def test1(inner: Function1[(Int,Int),Int]) = inner

val x1 = test {
  case (1,1) => 1
  case (x,y) => x+y
}

val x2 = test1 {
  case (1,1) => 1
  case (x,y) => x+y
}

// Same shit
x1(1,2)
x2(1,2)

//x1((1,2)) Not Enough parameters
x2((1,2))

sealed trait Control
case class Button(caption: String) extends Control
case class ComboBox(options: Seq[String]) extends Control
class RadioButton(val options: Seq[String]) extends Control

object RadioButton {
  def unapply(arg: RadioButton) = Some(arg.options)
}

object Control {
  def test(ctrl: Control): String = ctrl match {
    case Button("hi") => "Hi"
    case ComboBox(list) => list.mkString(", ")
    case RadioButton(opts) => opts.mkString(", ")
      //"something"
  }
}

Control.test(new RadioButton(Seq("hi", "lo")))

import example._

val intList = MyCons(1, MyCons(2, MyCons(3, MyNil)))


val plasticCan = new PlasticCan
val bottle = PlasticItem("bottle")
val apple = new Item("apple")
//plasticCan.add(apple)
plasticCan.add(bottle)

val gcan = new GarbageCan

gcan.add(apple)
gcan.add(bottle)


gcan.getItems

val bag = PlasticItem("bag")

val plasticItems = bottle :: bag :: MyNil

apple :: plasticItems

"hi" :: intList

List(1,2,3) contains "your mom"

import scalaz._

//"string" :: IList(1,2,3) won't compile

Can.add(1)
Can.list
var canList = Can.getItems()
canList
Can.getItems
//canList = 2 +: canList

Can.getItems
Can.list

MyList.dropWhile(MyList(1,2,3))( _ < 3)