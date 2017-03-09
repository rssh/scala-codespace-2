
trait Persistancy
{
  this: Cake =>

  def save[A](a:A)

}

trait Messaging
{
  this: Cake =>

  def send[A](a:A):Unit = ???

}

trait Logic
{
  this: Cake =>

  def doSomething(): Unit =
  {
    val a:Int = 3
    save(a)
    send(a)
  }

}

trait PersistancyImpl extends Persistancy
{
  this: Cake =>

  override def save[A](a:A): Unit =
  {
    System.err.println("A")
  }

}

trait LoggedPersistency extends Persistancy
{
  this: Cake =>

  abstract override def save[A](a:A): Unit =
  {
    System.err.println(s"saving $a")
    super.save(a)
  }

}

trait Cake extends Persistancy with Messaging
{

}

val a = new Cake with PersistancyImpl
                 with LoggedPersistency

