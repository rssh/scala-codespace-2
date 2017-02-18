package example

class Item(val name: String){
  override def toString() = this.getClass.getSimpleName + s"($name)"
}
case class PlasticItem(override val name: String) extends Item(name)

class GarbageCan[-T <: Item] {
  private[this] var list: List[T] = List.empty //TODO: why this has to be private?
  def add(item: T): Unit = {
    list = item +: list
  }

  def getItems = list.toString

  //def getState = list
}

object Can {
  var list: List[Int] = List.empty
  def add(i: Int) = list = i +: list
  def getItems() = list //TODO figure out
}

// (Int,Int) => Int
// Int => (Int => Int)
class PlasticCan extends GarbageCan[PlasticItem]
