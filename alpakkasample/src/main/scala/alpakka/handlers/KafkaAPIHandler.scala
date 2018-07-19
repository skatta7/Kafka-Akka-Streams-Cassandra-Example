package alpakka.handlers

import akka.actor
import akka.actor.ActorSystem
import akka.kafka.{ConsumerMessage, ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import alpakka.Programs.materializer
import alpakka.Programs.system
import akka.stream.scaladsl.{RunnableGraph, Source}
import alpakka.Programs.Event

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object KafkaAPIHandler {


  implicit val kafkaAPI = new alpakka.algebras.KafkaAPI.Handler[Future] {

    def getSource(consumerSettings:ConsumerSettings[String,String], topicName:String):Future[Source[Event, Consumer.Control]] = Future {
     val source = Consumer.committableSource(consumerSettings, Subscriptions.topics(topicName))
      source.map(msg => {println(s"===$msg==");Event(msg.record.timestamp().toString, msg.record.value)})
    }
    /*def materializer(runnableGraph:RunnableGraph[Nothing]) = Future {
      runnableGraph.run()
    }*/

  }
}
