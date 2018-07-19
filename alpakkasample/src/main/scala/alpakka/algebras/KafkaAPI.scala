package alpakka.algebras

import akka.kafka.ConsumerSettings
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.{RunnableGraph, Source}
import alpakka.Programs.Event
import freestyle.free._
import freestyle.free.implicits._

@free
trait KafkaAPI {
  def getSource(consumerSettings: ConsumerSettings[String, String], topicName: String): FS[Source[Event, Consumer.Control]]
  def materializer(runnableGraph:RunnableGraph[Nothing]):FS[Nothing]

}
