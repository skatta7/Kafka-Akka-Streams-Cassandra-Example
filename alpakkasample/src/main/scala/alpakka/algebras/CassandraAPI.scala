package alpakka.algebras

import akka.NotUsed
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.{Flow, Source}
import alpakka.Programs.Event
import com.datastax.driver.core.{Cluster, PreparedStatement, Session}
import freestyle.free._

import scala.collection.immutable
import scala.concurrent.Future

@free
trait CassandraAPI {
  def getCluster(contactPoints: String, port: Int, username: String, password: String): FS[Cluster]

  def getSession(keySpace: String, cluster: Cluster): FS[Session]
  def prepare(session: Session, query: Option[String]): FS[Option[PreparedStatement]]
  def getFlow (preparedStatement:PreparedStatement, session:Session): FS[Flow[Event, Event, NotUsed]]
  def materialize(source: Source[Event, Consumer.Control],flow:Flow[Event, Event, NotUsed]): FS[String]
}
