import com.opencsv.CSVReader
import scala.collection.JavaConverters._
import java.nio.file.{Paths, Path}
import java.net.URLDecoder
import scala.collection.mutable.ListBuffer

object CsvReaderExample {
  def readCSV(filename: String): List[List[String]] = {
    val reader = new CSVReader(new java.io.FileReader(filename))
    try {
      reader.readAll().asScala.map(_.toList).toList
    } finally {
      reader.close()
    }
  }

  def main(filename: String): List[List[String]] = {
    val path = getClass.getResource("").getPath.replaceAll("/", "\\\\")
    val decodedPath = URLDecoder.decode(path, "UTF-8")
    val pathString = decodedPath.toString
    val grandparentDirectory = pathString.split("\\\\").dropRight(3).mkString("\\\\")
    val ressourcesDirectory = grandparentDirectory + "\\\\src\\\\main\\\\ressources"
    println(ressourcesDirectory)
    val data = readCSV(ressourcesDirectory + "\\\\" + filename)
    data
  }
}




case class Album(
                  id : String,
                  name: String,
                  album_type: String,
                  total_tracks: String,
                  release_date: String,
                  restrictions : String,
                  genres: List[String],
                  popularity: String,
                  external_urls : String,
                  id_artists: List[String],
                  id_tracks: List[String]
                )


trait IBaseRepository[T] {
  val mutableList = ListBuffer[T]()
  def getAll(): ListBuffer[T]
  def getById(id: String): Option[T]
  def getAllByGenre(genre: String): ListBuffer[T]

  def getAllByGenreAscPopularity(genre: String): ListBuffer[T]
  def getAllByGenreDescPopularity(genre: String): ListBuffer[T]

  def getAllByAscPopularity(): ListBuffer[T]
  def getAllByDescPopularity(): ListBuffer[T]

  def getAllAveragePopularityByGenre(): ListBuffer[T]
}


object AlbumRepository extends IBaseRepository[Album] {
  val albums = CsvReaderExample.main("albums.csv")
  val albumsMutableList: ListBuffer[Album] = ListBuffer()
  for (album <- albums) {
    for (index <- album) {
      val parts: List[String] = index.split(";").toList

      val albumGenres: List[String] = parts(6).split(" ").toList
      val artistIds: List[String] = parts(9).split(" ").toList
      val trackIds: List[String] = parts(10).split(" ").toList
      val album = Album(parts(0), parts(1), parts(2), parts(3), parts(4), parts(5), albumGenres, parts(7), parts(8), artistIds, trackIds)
      albumsMutableList += album
    }
  }

  override def getAll(): ListBuffer[Album] = albumsMutableList
  override def getById(id: String): Option[Album] = albumsMutableList.find(_.id == id)
  override def getAllByGenre(genre: String): ListBuffer[Album] = albumsMutableList.filter(_.genres.contains(genre))

  override def getAllByGenreAscPopularity(genre: String): ListBuffer[Album] = getAllByGenre(genre).sortWith(_.popularity < _.popularity)
  override def getAllByGenreDescPopularity(genre: String): ListBuffer[Album] = getAllByGenre(genre).sortWith(_.popularity > _.popularity)

  override def getAllByAscPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity < _.popularity)
  override def getAllByDescPopularity(): ListBuffer[Album] = albumsMutableList.sortWith(_.popularity > _.popularity)

  override def getAllAveragePopularityByGenre(): ListBuffer[Album] = ???
}

AlbumRepository.getAllByGenre("rock").foreach(println)



