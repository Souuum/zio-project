package repositories.implementation

import batchs.CsvReaderBatch
import scala.collection.mutable.ListBuffer
import repositories.interface.IBaseRepository
import entities.Album
import zio.ZIO
import zio.Console._
import java.io.IOException
object AlbumRepository extends IBaseRepository[Album] {
  val albums = CsvReaderBatch.beginRead("Albums.csv")
  val albumsMutableList: ListBuffer[Album] = ListBuffer()
  for (album <- albums) {
    println(album)
    val albumAlbumoAdd = album match {
      case List(
            id,
            name,
            album_type,
            total_tracks,
            release_date,
            popularity,
            external_urls,
            id_artistsList,
            id_tracksList
          ) =>
        val id_artists = id_artistsList.split(" ").map(_.trim).toList
        val id_tracks = id_tracksList.split(" ").map(_.trim).toList
        Album(
          id,
          name,
          album_type,
          total_tracks,
          release_date,
          popularity,
          external_urls,
          id_artists,
          id_tracks
        )
    }
    albumsMutableList += albumAlbumoAdd
  }

  override def getAll(): ZIO[Album, IOException, ListBuffer[Album]] = {
    for {
      _ <- printLine("Récupération des albums")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
    } yield albumsMutableList
  }
  override def getById(id: String): Option[Album] =
    albumsMutableList.find(_.id == id)

  override def getAllByAscPopularity()
      : ZIO[Album, IOException, ListBuffer[Album]] = {
    for {
      _ <- printLine("Trie des albums par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield albumsMutableList.sortWith(_.popularity < _.popularity)
  }
  override def getAllByDescPopularity()
      : ZIO[Album, IOException, ListBuffer[Album]] = {
    for {
      _ <- printLine("Trie des albums par popularité")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Trie terminé !")
    } yield albumsMutableList.sortWith(_.popularity > _.popularity)
  }

  override def getAllAveragePopularityByGenre()
      : ZIO[Album, IOException, ListBuffer[Album]] = ???
}
