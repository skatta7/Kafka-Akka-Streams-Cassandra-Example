name := "alpakkasample"

version := "0.1"

scalaVersion := "2.12.3"

addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10"  cross CrossVersion.full)

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.13"
libraryDependencies += "com.typesafe.akka" %% "akka-stream-kafka" % "0.22"
libraryDependencies += "com.lightbend.akka" %% "akka-stream-alpakka-cassandra" % "0.20"
libraryDependencies += "com.datastax.cassandra" % "cassandra-driver-core" % "3.3.0"

libraryDependencies += "io.frees" %% "frees-core" % "0.7.0"
libraryDependencies += "org.typelevel" %% "cats-free" % "1.1.0"
