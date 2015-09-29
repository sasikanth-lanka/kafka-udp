package logger

import akka.actor.SupervisorStrategy._
import akka.actor._

import scala.concurrent.duration._


class Supervisor extends Actor {

  override def supervisorStrategy: SupervisorStrategy = {
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ActorInitializationException => Stop
      case _: ActorKilledException => Restart
      case _: Exception => Restart
    }
  }

  def receive = {
    case props: Props => context.actorOf(props)
    case _ =>
  }

}
