name := """play-scalikejdbc"""
organization := "org.ababup1192"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-config" % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.0-scalikejdbc-3.3",
  "org.scalikejdbc" %% "scalikejdbc-play-fixture" % "2.7.0-scalikejdbc-3.3",
  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "3.3.2",
  "org.flywaydb" %% "flyway-play" % "5.2.0",
  "com.h2database" % "h2" % "1.4.197",
  "mysql" % "mysql-connector-java" % "8.0.15",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  guice,
  "org.scalikejdbc" %% "scalikejdbc-test" % "3.3.2" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test,
  "org.mockito" % "mockito-core" % "2.25.1" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "org.ababup1192.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "org.ababup1192.binders._"
