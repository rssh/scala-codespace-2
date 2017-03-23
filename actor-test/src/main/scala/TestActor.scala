import akka.actor.{Actor, Props}

class UnknownMessageException(msg: Any) extends Exception

class TestActor(name: String) extends Actor {
  def receive = {
    case "Hi" =>
      println("received Hi")
      sender ! "Hello"
    case any => println("Received unknown message")
      throw new UnknownMessageException(any)
  }

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"actor $name is restarting because of $reason after receiving $message")
    //super.preRestart(reason,message)
  }
}

object TestActor{
  def props(name: String) = Props(new TestActor(name))
}
