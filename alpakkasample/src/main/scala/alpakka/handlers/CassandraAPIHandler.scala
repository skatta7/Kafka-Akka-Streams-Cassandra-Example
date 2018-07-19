package alpakka.handlers

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
  }
}
