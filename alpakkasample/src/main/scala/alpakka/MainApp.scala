package alpakka

import scala.concurrent.Future
import freestyle.free._
import freestyle.free.implicits._
import cats.implicits._

import alpakka.handlers.CassandraAPIHandler._
import alpakka.handlers.KafkaAPIHandler._
import scala.concurrent.ExecutionContext.Implicits.global

object MainApp extends App {
  val interpretedValue = Programs.act().interpret[Future]
}
