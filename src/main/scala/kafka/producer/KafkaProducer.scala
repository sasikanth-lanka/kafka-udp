package kafka.producer

import java.util.{Properties, UUID}

import kafka.message.{DefaultCompressionCodec, NoCompressionCodec}

case class KafkaProducer(brokerList: String,
                         clientId: String = UUID.randomUUID().toString,
                         synchronously: Boolean = true,
                         compress: Boolean = true,
                         batchSize: Integer = 200,
                         messageSendMaxRetries: Integer = 1,
                         requestRequiredAcks: Integer = 0) {

  val props = new Properties()

  val codec = if (compress) DefaultCompressionCodec.codec else NoCompressionCodec.codec

  props.put("compression.codec", codec.toString)
  props.put("producer.type", if (synchronously) "sync" else "async")
  props.put("metadata.broker.list", brokerList)
  props.put("batch.num.messages", batchSize.toString)
  props.put("message.send.max.retries", messageSendMaxRetries.toString)
  props.put("request.required.acks", requestRequiredAcks.toString)
  props.put("client.id", clientId.toString)

  val producer = new Producer[AnyRef, AnyRef](new ProducerConfig(props))

  def send(topic: String, message: String): Unit = send(topic, message.getBytes("UTF8"))

  def send(topic: String, message: Array[Byte]): Unit = {
    try {
      val km: KeyedMessage[AnyRef, AnyRef] = new KeyedMessage(topic, message)
      producer.send(km)
    } catch {
      case e: Exception =>
    }

  }
  def close() = producer.close()
}
