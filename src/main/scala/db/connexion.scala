import java.io.InputStream
import zio._
import zio.jdbc._
import zio.json._
import zio.stream._

case class Logs(user: String, password: String)

object Logs {
  implicit val decoder: JsonDecoder[Logs] = DeriveJsonDecoder.gen[Logs]
}

// récupérer le fichier logs.json
val stream: InputStream = getClass.getResourceAsStream("/logs.json")
val json: String = scala.io.Source.fromInputStream(stream).mkString.toString

// transformer le fichier json en objet Logs
val logs: Logs = json.fromJson[Logs].getOrElse(Logs("", ""))

// créer un Map pour la connexion à la base de données
val properties = Map(
  "user" -> logs.user,
  "password" -> logs.password
)

// créer un ZLayer pour la connexion à la base de données
val createZIOPoolConfig: ULayer[ZConnectionPoolConfig] =
  ZLayer.succeed(ZConnectionPoolConfig.default)

val connectionPool: ZLayer[ZConnectionPoolConfig, Throwable, ZConnectionPool] =
  ZConnectionPool.mysql("localhost", 3306, "mysql", properties)

val live: ZLayer[ZConnectionPoolConfig, Throwable, ZConnectionPool] =
  createZIOPoolConfig >>> connectionPool
