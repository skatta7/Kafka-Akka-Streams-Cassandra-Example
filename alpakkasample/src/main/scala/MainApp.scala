import akka.stream._
import akka.stream.scaladsl._
import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._
import java.nio.file.Paths

import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.kafka.scaladsl.Consumer.DrainingControl
import com.datastax.driver.core.Cluster
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.ExecutionContext.Implicits.global

object MainApp extends App {/*

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()

  val config = system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

  val source =
    Consumer
      .committableSource(consumerSettings, Subscriptions.topics("events"))

  val sourceToCassandra = source
    .map(msg => {println(s"===$msg==");CassandraConnection.Event(msg.record.timestamp().toString, msg.record.value)})
 val results =  sourceToCassandra.via(CassandraConnection.getCassandraFlow()).runWith(Sink.seq)*/

 //source.mapAsync(1)(msg => msg.committableOffset.commitScaladsl()).runWith(Sink.ignore)

}
