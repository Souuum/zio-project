import sbt.Keys.testFrameworks

val scala3Version: String = "3.3.1"
val zioVersion: String = "2.0.20"
val zioHttpVersion: String = "3.0.0-RC2"
val zioJsonVersion: String = "0.5.0"
val zioJdbcVersion: String = "0.1.1"
val zioSchemaVersion = "0.4.11"
val zioSqlVersion: String = "0.1.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "zio-project",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion,
      "dev.zio" %% "zio-http" % zioHttpVersion,
      "dev.zio" %% "zio-json" % zioJsonVersion,
      "dev.zio" %% "zio-schema" % zioSchemaVersion,
      "dev.zio" %% "zio-jdbc" % zioJdbcVersion,
      "dev.zio" %% "zio-test" % "2.0.15" % Test,
      "dev.zio" %% "zio-test-sbt"      % "2.1-RC1" % Test,
      "dev.zio" %% "zio-test-magnolia" % "2.1-RC1" % Test,
      "com.softwaremill.sttp.client3" %% "core" % "3.8.13",
      "com.softwaremill.sttp.client3" %% "async-http-client-backend-future" % "3.8.13",
      "io.d11" %% "zhttp" % "2.0.0-RC10",
      "io.circe" %% "circe-generic" % "0.14.5",
      "io.circe" %% "circe-parser" % "0.14.5",
      "com.opencsv" % "opencsv" % "5.7.1",
      "au.com.bytecode" % "opencsv" % "2.4"
    )
)
