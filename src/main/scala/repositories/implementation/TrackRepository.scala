package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Track
import scala.collection.mutable.ListBuffer

object TrackRepository extends IBaseRepository[Track]{
  val tracks = CsvReaderBatch.beginRead("Tracks.csv")
  val tracksMutableList: ListBuffer[Track] = ListBuffer()
  for (track <- tracks) {
    val trackToAdd = track match {
      case List(id, name, popularity, explicit, url, id_album, id_artistsList) =>
        val id_artists = id_artistsList.split(" ").map(_.trim).toList
        Track(id, name, popularity, explicit, url, id_album, id_artists)
      case _ =>
        throw new IllegalArgumentException("Invalid list format")
    }
    tracksMutableList += trackToAdd

  }

  override def getAll(): ListBuffer[Track] = tracksMutableList
  override def getById(id: String): Option[Track] = tracksMutableList.find(_.id == id)

  override def getAllByAscPopularity(): ListBuffer[Track] = tracksMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Track] = tracksMutableList.sortWith(_.popularity > _.popularity)

  override def getAllAveragePopularityByGenre(): ListBuffer[Track] = ???
}