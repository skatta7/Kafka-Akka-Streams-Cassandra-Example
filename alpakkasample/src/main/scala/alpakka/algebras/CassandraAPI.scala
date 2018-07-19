package alpakka.algebras

import com.datastax.driver.core.{Cluster, PreparedStatement, Session}
import freestyle.free._

@free
trait CassandraAPI {
  def getCluster(contactPoints: String, port: Int, username: String, password: String): FS[Cluster]

  def getSession(keySpace: String, cluster: Cluster): FS[Session]
  def prepare(session: Session, query: Option[String]): FS[Option[PreparedStatement]]
}
