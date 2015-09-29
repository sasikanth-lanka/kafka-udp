import com.typesafe.sbt.packager.archetypes.ServerLoader

name := """kafka-udp"""

version := "1.0"

scalaVersion := "2.11.7"

maintainer := "zlanka"

packageSummary := "Kafka Thrift Server Debian Package"

packageDescription := """Kafka linking for php"""

enablePlugins(JavaServerAppPackaging)

enablePlugins(DebianPlugin)

serverLoading in Debian := ServerLoader.SystemV

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.apache.thrift" % "libthrift" % "0.9.2",
  "org.apache.kafka" %% "kafka" % "0.8.2.2",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14",
  "com.typesafe.play" %% "play-json" % "2.3.1"
)

