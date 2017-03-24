import akka.actor.{Actor, ActorRef, Props, Terminated}

class DeathWatcher(watched: ActorRef) extends Actor {
  context.watch(watched)
  def receive = {
    case Terminated(_) => println("watched actor was terminated")
  }
}

object DeathWatcher{
  def props(a: ActorRef) = Props(new DeathWatcher(a))
}
