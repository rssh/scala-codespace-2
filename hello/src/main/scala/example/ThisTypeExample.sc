

trait ThisTypeExample
{

  def me: this.type

  def incr: this.type

}

class MyExample extends ThisTypeExample
{

  var x = 0

  override def me: this.type = this

  override def incr: this.type  =
  {
   x += 1
   this
  }

  // will not compile
  //def copy: this.type  =
  //{
  //  new MyExample()
  //}

}

val x = new MyExample
//val y = x.copy