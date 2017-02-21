package example

class Item(val name: String){
  override def toString() = this.getClass.getSimpleName + s"($name)"
}
case class PlasticItem(override val name: String) extends Item(name)

class GarbageCan[-T <: Item] {

  private[this] var list: List[T] = List.empty //TODO: why this has to be private?

  //val list1: List[T] = List.empty //TODO: why this has to be


  def add(item: T): Unit = {
    list = item +: list
  }

  def getItems = list.toString

  //def getState = list
}



// X <: Y => F[X] <: F[Y]
//  F[A] out A
//------------------

// f:

// f: - A => + F[A]

// A => (B => C)

// f: A=>B;  g: B=>C

//  f |> g = (f andThen g)
//
//  f': A' => B'  A' >: A,  B' <: B [   <: f

//



// f1: B => F[B]

// A <: B

//  want:  F[A] <: F[B]
// f: - B => + F[A]

object Can {
  var list: List[Int] = List.empty
  def add(i: Int) = list = i +: list
  def getItems() = list //TODO figure out
}

// (Int,Int) => Int
// Int => (Int => Int)
class PlasticCan extends GarbageCan[PlasticItem]
