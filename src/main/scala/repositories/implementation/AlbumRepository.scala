package repositories.implementation

import batchs.CsvReaderBatch
import scala.collection.mutable.ListBuffer
import repositories.interface.IBaseRepository
import entities.Album
object AlbumRepository extends IBaseRepository[Album] {
  val albums = CsvReaderBatch.beginRead("Albums.csv")
  val albumsMutableList: ListBuffer[Album] = ListBuffer()
  for (album <- albums) {
    println(album)
    val albumToAdd = album match {
      case List(id, name, album_type, total_tracks, release_date, popularity, external_urls, id_artistsList, id_tracksList) =>
        val id_artists = id_artistsList.split(" ").map(_.trim).toList
        val id_tracks = id_tracksList.split(" ").map(_.trim).toList
        Album(id, name, album_type, total_tracks, release_date, popularity, external_urls, id_artists, id_tracks)


    }
    albumsMutableList += albumToAdd
  }

  override def getAll(): ListBuffer[Album] = albumsMutableList
  override def getById(id: String): Option[Album] = albumsMutableList.find(_.id == id)
  override def getAllByAscPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity > _.popularity)
  override def getAllAveragePopularityByGenre(): ListBuffer[Album] = ???
}
