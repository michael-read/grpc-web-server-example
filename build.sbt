
name := "grpc-web-server"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / scalacOptions += "-deprecation"

lazy val AkkaVersion = "2.7.0"
lazy val AkkaHttpVersion = "10.4.0"
lazy val AkkaGrpcVersion = "2.2.0"  // this is here to document what should be in the plugins.sbt
lazy val AkkaCORS = "1.1.3"

/*
Using these versions are causing errors in the UI with request headers.

lazy val AkkaVersion = "2.6.17"
lazy val AkkaHttpVersion = "10.2.7" // note: ch.megard:akka-http-cors_2.13:1.1.3 evicts for 10.2.8
lazy val AkkaGrpcVersion = "2.1.4"  // this is here to document what should be in the plugins.sbt
lazy val AkkaCORS = "1.1.3"
*/

lazy val logbackVersion  = "1.2.3"

enablePlugins(AkkaGrpcPlugin)

akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Java)
akkaGrpcCodeGeneratorSettings += "server_power_apis"

val scalaTestVersion = {
  "3.1.4"
}
val scalaTestScalaCheckVersion = {
  "1-14"
}
val scalaCheckVersion = "1.15.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,

  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,

  // CORS for grpc-web
  "ch.megard" %% "akka-http-cors" % AkkaCORS,

  "org.projectlombok" % "lombok" % "1.18.24" % "provided",
//  "org.projectlombok" % "lombok" % "1.18.20" % "provided",

  // The 'scalaTestPlus' projects are independently versioned,
  // but the version of each module starts with the scalatest
  // version it was intended to work with
  "org.scalatestplus" %% "junit-4-13" % (scalaTestVersion + ".0") % "test", // ApacheV2
  "org.scalatestplus" %% "testng-6-7" % (scalaTestVersion + ".0") % "test", // ApacheV2
  "org.scalatestplus" %% s"scalacheck-${scalaTestScalaCheckVersion}" % (scalaTestVersion + ".0") % "test", // ApacheV2
  "org.scalatestplus" %% "mockito-3-4" % (scalaTestVersion + ".0") % "test", // ApacheV2

  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % Test,
  "junit" % "junit" % "4.13.2" % Test
)