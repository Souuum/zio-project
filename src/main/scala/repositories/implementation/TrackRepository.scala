package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Track
import scala.collection.mutable.ListBuffer

object TrackRepository extends IBaseRepository[Track]{
  val tracks = CsvReaderBatch.beginRead("tracks.csv")
  val tracksMutableList: ListBuffer[Track] = ListBuffer()
  for (track <- tracks) {
    for (index <- track) {
      val parts: List[String] = index.split(";").toList

      val track = Track(parts(0), parts(1), parts(2), parts(3), parts(4), parts(5).split(" ").toList, parts(6).split(" ").toList)
      tracksMutableList += track
    }
  }

  override def getAll(): ListBuffer[Track] = tracksMutableList
  override def getById(id: String): Option[Track] = tracksMutableList.find(_.id == id)

  override def getAllByAscPopularity(): ListBuffer[Track] = tracksMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Track] = tracksMutableList.sortWith(_.popularity > _.popularity)

  override def getAllAveragePopularityByGenre(): ListBuffer[Track] = ???
}