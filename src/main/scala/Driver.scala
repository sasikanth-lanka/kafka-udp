import akka.actor.{ActorSystem, Props}
import logger.{Receiver, Supervisor}

object Driver {

  def main(args: Array[String]) {
    val supervisor = ActorSystem("kafka-receiver").actorOf(Props[Supervisor])
    supervisor ! Props(new Receiver(9090))
  }
  
}