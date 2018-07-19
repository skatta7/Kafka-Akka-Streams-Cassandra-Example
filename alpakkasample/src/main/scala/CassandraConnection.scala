import akka.stream.alpakka.cassandra.CassandraBatchSettings
import akka.stream.alpakka.cassandra.scaladsl.CassandraFlow

import com.datastax.driver.core.{Cluster, PreparedStatement}

object CassandraConnection {

 /* implicit val session = Cluster.builder
    .addContactPoint("127.0.0.1")
    .withPort(9042)
    .build
    .connect()

 // case class Event(id: String, eventMsg: String)

  def getCassandraFlow() ={
    val preparedStatement = session.prepare(s"INSERT INTO alpakka_space.events(id, event) VALUES (?,?)")
    val statementBinder =
      (elemToInsert: Event, statement: PreparedStatement) => statement.bind(elemToInsert.id, elemToInsert.eventMsg)

    val settings: CassandraBatchSettings = CassandraBatchSettings.Defaults


    CassandraFlow.createUnloggedBatchWithPassThrough[Event, String](parallelism = 2,
      preparedStatement,
      statementBinder,
      ti => ti.id,
      settings)*/
 // }
}
