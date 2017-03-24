import akka.actor.{ActorSystem, Kill, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._

import scala.concurrent.Await
import scala.concurrent.duration._


object ActorTest {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem()

    implicit val timeout = Timeout(5 seconds)

    val newActor = system.actorOf(TestActor.props("go"))
    val watcher = system.actorOf(DeathWatcher.props(newActor))
    newActor ! TestActor
    val responseFuture = newActor ? "Hi"
    println(s"received response ${Await.result(responseFuture, 1 second)}")
    //newActor ! PoisonPill
    newActor ! Kill
    //system stop newActor
    Thread.sleep(1000)
    system.terminate()
  }
}
