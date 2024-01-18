package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Artist
import scala.collection.mutable.ListBuffer


object ArtistRepository extends IBaseRepository[Artist]{
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

  override def getAll(): ListBuffer[Artist] = artistsMutableList
  override def getById(id: String): Option[Artist] = artistsMutableList.find(_.id == id)

  override def getAllByAscPopularity(): ListBuffer[Artist] = artistsMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Artist] = artistsMutableList.sortWith(_.popularity > _.popularity)

  override def getAllAveragePopularityByGenre(): ListBuffer[Artist] = ???

  def getAllByGenre(genre: String): ListBuffer[Artist] = artistsMutableList.filter(_.genres.contains(genre))
  def getAllByGenreAscPopularity(genre: String): ListBuffer[Artist] = getAllByGenre(genre).sortWith(_.popularity < _.popularity)
  def getAllByGenreDescPopularity(genre: String): ListBuffer[Artist] = getAllByGenre(genre).sortWith(_.popularity > _.popularity)
}