package alpakka.handlers

import akka.NotUsed
import akka.kafka.scaladsl.Consumer
import akka.stream.alpakka.cassandra.scaladsl.CassandraFlow
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.{Flow, Source}
import alpakka.Programs.{Event, settings, statementBinder, materializer}
import com.datastax.driver.core.Cluster.Builder
import com.datastax.driver.core.{Cluster, PreparedStatement, Session}
import com.datastax.driver.core.policies.{DCAwareRoundRobinPolicy, TokenAwarePolicy}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object CassandraAPIHandler {
  implicit val cassandraAPI = new alpakka.algebras.CassandraAPI.Handler[Future] {

    def getCluster(contactPoints: String, port: Int, username: String, password: String): Future[Cluster] = Future.successful({
      val builder: Builder = Cluster
        .builder()
        .addContactPoints(contactPoints.split(","): _*)
        .withCredentials(username, password)
        .withPort(port)
        .withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().build()))

      builder.build()
    })

    def getSession(keySpace: String, cluster: Cluster): Future[Session] = Future {
      cluster.connect(keySpace)
    }

    def prepare(session: Session, query: Option[String]): Future[Option[PreparedStatement]] = Future {
      query.isDefined match {
        case true => Some(session.prepare(query.get))
        case false => None
      }
    }

    def  getFlow (preparedStatement:PreparedStatement, session: Session): Future[Flow[Event, Event, NotUsed]] = Future{
      CassandraFlow.createUnloggedBatchWithPassThrough[Event, String](parallelism = 2,
        preparedStatement,
        statementBinder,
        ti => ti.id,
        settings)(session)
    }

    def materialize(source: Source[Event, Consumer.Control],flow:Flow[Event, Event, NotUsed]): Future[String] = Future{
      source.via(flow).runWith(Sink.seq)
      "Success"
    }
  }
}
