package alpakka

import alpakka.algebras.{CassandraAPI, KafkaAPI}
import freestyle.free.module

object Modules {
  @module trait AlgebrasModule {

    val cassandraApi:CassandraAPI
    val kafkaApi:KafkaAPI
  }
}
