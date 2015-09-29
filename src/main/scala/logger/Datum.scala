package logger

import play.api.libs.json.{JsValue, Json}

case class Datum(topic: String, data: JsValue)

object Datum {
  implicit val writes = Json.writes[Datum]
  implicit val reads = Json.reads[Datum]
  implicit val format = Json.format[Datum]
}