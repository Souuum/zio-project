package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Artist
import scala.collection.mutable.ListBuffer


object ArtistRepository extends IBaseRepository[Artist]{
  val artists = CsvReaderBatch.beginRead("artists.csv")
  val artistsMutableList: ListBuffer[Artist] = ListBuffer()
  for (artist <- artists) {
    for (index <- artist) {
      val parts: List[String] = index.split(";").toList

      val artist = Artist(parts(0), parts(1), parts(2).split(" ").toList, parts(3), parts(4))
      artistsMutableList += artist
    }
  }

  override def getAll(): ListBuffer[Artist] = artistsMutableList
  override def getById(id: String): Option[Artist] = artistsMutableList.find(_.id == id)

  override def getAllByAscPopularity(): ListBuffer[Artist] = artistsMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Artist] = artistsMutableList.sortWith(_.popularity > _.popularity)

  override def getAllAveragePopularityByGenre(): ListBuffer[Artist] = ???

  def getAllByGenre(genre: String): ListBuffer[Artist] = artistsMutableList.filter(_.genres.contains(genre))
  def getAllByGenreAscPopularity(genre: String): ListBuffer[Artist] = getAllByGenre(genre).sortWith(_.popularity < _.popularity)
  def getAllByGenreDescPopularity(genre: String): ListBuffer[Artist] = getAllByGenre(genre).sortWith(_.popularity > _.popularity)
}