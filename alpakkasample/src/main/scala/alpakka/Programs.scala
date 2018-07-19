package alpakka


import akka.actor.ActorSystem
import akka.kafka.ConsumerSettings
import akka.stream.ActorMaterializer
import akka.stream.alpakka.cassandra.CassandraBatchSettings
import akka.stream.alpakka.cassandra.scaladsl.CassandraFlow
import akka.stream.scaladsl.Sink
import alpakka.Modules.AlgebrasModule
import com.datastax.driver.core.PreparedStatement
import freestyle.free._
import freestyle.free.implicits
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.Future

object Programs {

  implicit val system = ActorSystem("QuickStart")
  implicit val materializer = ActorMaterializer()
  case class Event(id: String, eventMsg: String)
  //cassandra details
  val query = Some(s"INSERT INTO alpakka_space.events(id, event) VALUES (?,?)")

  val statementBinder = (elemToInsert: Event, statement: PreparedStatement) => statement.bind(elemToInsert.id, elemToInsert.eventMsg)
  val settings: CassandraBatchSettings = CassandraBatchSettings.Defaults


  //kafka consumer details
  val config = system.settings.config.getConfig("akka.kafka.consumer")
  val consumerSettings =
    ConsumerSettings(config, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

//program
  def act()(implicit module: AlgebrasModule[AlgebrasModule.Op]): FreeS[AlgebrasModule.Op, String] = {

    for {
      source <- module.kafkaApi.getSource(consumerSettings,"events")
      cluster <- module.cassandraApi.getCluster("127.0.0.1", 9042, "", "")
      session <- module.cassandraApi.getSession("alpakka_space", cluster)
      preparedStatement <- module.cassandraApi.prepare(session,query)
      flow <- module.cassandraApi.getFlow(preparedStatement.get,session)
      a <- module.cassandraApi.materialize(source,flow)
    } yield a
  }

}
