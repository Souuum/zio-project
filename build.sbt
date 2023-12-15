val zioVersion: String = "2.0.20"
val scala3Version: String = "3.3.1"
lazy val root = project
  .in(file("."))
  .settings(
    name := "zio-project",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-streams" % zioVersion
    )
  )
