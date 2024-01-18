package repositories.implementation

import batchs.CsvReaderBatch
import scala.collection.mutable.ListBuffer
import repositories.interface.IBaseRepository
import entities.Album
object AlbumRepository extends IBaseRepository[Album] {
  val albums = CsvReaderBatch.beginRead("albums.csv")
  val albumsMutableList: ListBuffer[Album] = ListBuffer()
  for (album <- albums) {
    for (index <- album) {
      val parts: List[String] = index.split(";").toList

      val album = Album(parts(0), parts(1), parts(2), parts(3), parts(4), parts(5), parts(6), parts(7).split(" ").toList, parts(8).split(" ").toList)
      albumsMutableList += album
    }
  }

  override def getAll(): ListBuffer[Album] = albumsMutableList
  override def getById(id: String): Option[Album] = albumsMutableList.find(_.id == id)
  override def getAllByAscPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity > _.popularity)
  override def getAllAveragePopularityByGenre(): ListBuffer[Album] = ???
}
