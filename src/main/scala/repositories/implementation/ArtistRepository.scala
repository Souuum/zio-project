package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Artist
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer

object ArtistRepository extends IBaseRepository[Artist] {
  val artists = CsvReaderBatch.beginRead("Artists.csv")
  val artistsMutableList: ListBuffer[Artist] = ListBuffer()
  for (artist <- artists) {
    val artistToAdd = artist match {
      case List(id, name, genreList, popularity, url) =>
        val genres = genreList.split(" ").map(_.trim).toList
        Artist(id, name, genres, popularity, url)

      case _ =>
        throw new IllegalArgumentException("Invalid list format")
    }
    artistsMutableList += artistToAdd
  }

  override def getAll(): ZIO[Artist, IOException, ListBuffer[Artist]] = {
    for {
      _ <- printLine("Récupération des artistes")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
    } yield artistsMutableList
  }
  override def getById(id: String): Option[Artist] =
    artistsMutableList.find(_.id == id)
  override def getAllByAscPopularity()
      : ZIO[Artist, IOException, ListBuffer[Artist]] = {
    for {
      _ <- printLine("Trie des artistes par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield artistsMutableList.sortWith(_.popularity < _.popularity)
  }
  override def getAllByDescPopularity()
      : ZIO[Artist, IOException, ListBuffer[Artist]] = {
    for {
      _ <- printLine("Trie des artistes par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield artistsMutableList.sortWith(_.popularity > _.popularity)
  }

  override def getAllAveragePopularityByGenre()
      : ZIO[Artist, IOException, ListBuffer[Artist]] = ???

  def getByGenre(
      genre: String
  ): ZIO[Artist, IOException, ListBuffer[Artist]] =
    for {
      _ <- printLine("Récupération des aritstes")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
    } yield artistsMutableList.filter(_.genres.contains(genre))
}
