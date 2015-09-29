package logger

import java.net.InetSocketAddress

import akka.actor._
import akka.io.{IO, Udp}
import kafka.producer.KafkaProducer
import play.api.libs.json.Json

class Receiver(port: Int) extends Actor {

  import context.system

  val producer = new KafkaProducer("localhost:9092", synchronously = false)

  IO(Udp) ! Udp.Bind(self, new InetSocketAddress("0.0.0.0", port))

  def receive = {
    case Udp.Received(data, remote) =>
      try {
        Json.parse(data.toArray).asOpt[Datum].foreach { datum =>
          producer.send(datum.topic, Json.stringify(datum.data))
        }
      } catch {
        case t: Throwable =>
      }
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    try {
      super.postStop()
      producer.close()
    } catch {
      case e: Throwable =>
    }
  }

}
