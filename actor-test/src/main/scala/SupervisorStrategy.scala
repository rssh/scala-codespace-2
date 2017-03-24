import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{OneForOneStrategy, SupervisorStrategyConfigurator}
import scala.concurrent.duration._

class SupervisorStrategy extends SupervisorStrategyConfigurator {
  def create = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: UnknownMessageException => Stop
    case _: Exception                => Restart
  }
}
