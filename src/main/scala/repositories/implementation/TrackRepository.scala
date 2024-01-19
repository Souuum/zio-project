package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Track
import zio.ZIO
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer

object TrackRepository extends IBaseRepository[Track] {
  val tracks = CsvReaderBatch.beginRead("Tracks.csv")
  val tracksMutableList: ListBuffer[Track] = ListBuffer()
  for (track <- tracks) {
    val trackToAdd = track match {
      case List(
            id,
            name,
            popularity,
            explicit,
            url,
            id_album,
            id_tracksList
          ) =>
        val id_tracks = id_tracksList.split(" ").map(_.trim).toList
        Track(id, name, popularity, explicit, url, id_album, id_tracks)
      case _ =>
        throw new IllegalArgumentException("Invalid list format")
    }
    tracksMutableList += trackToAdd

  }

  override def getAll(): ZIO[Any, IOException, ListBuffer[Track]] = {
    for {
      _ <- printLine("Récupération des tracks")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
    } yield tracksMutableList
  }
  override def getById(id: String): Option[Track] =
    tracksMutableList.find(_.id == id)
  override def getAllByAscPopularity()
      : ZIO[Any, IOException, ListBuffer[Track]] = {
    for {
      _ <- printLine("Trie des tracks par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield tracksMutableList.sortWith(_.popularity < _.popularity)
  }
  override def getAllByDescPopularity()
      : ZIO[Any, IOException, ListBuffer[Track]] = {
    for {
      _ <- printLine("Trie des tracks par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield tracksMutableList.sortWith(_.popularity > _.popularity)
  }

  override def getAllAveragePopularityByGenre()
      : ZIO[Any, IOException, ListBuffer[Track]] = ???
}
