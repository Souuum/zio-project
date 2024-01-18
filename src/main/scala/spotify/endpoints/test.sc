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
                  popularity: String,
                  external_urls : String,
                  id_artists: List[String],
                  id_tracks: List[String]
                )
case class Track(
                  id : String,
                  name: String,
                  popularity: String,
                  explicit : String,
                  external_urls : String,
                  id_artists: List[String],
                  id_tracks: List[String]
                )
case class Artist(
                    id : String,
                    name: String,
                    genres: List[String],
                    popularity: String,
                    external_urls : String
                 )


trait IBaseRepository[T] {
  val mutableList = ListBuffer[T]()
  def getAll(): ListBuffer[T]
  def getById(id: String): Option[T]

  def getAllByAscPopularity(): ListBuffer[T]
  def getAllByDescPopularity(): ListBuffer[T]

  def getAllAveragePopularityByGenre(): ListBuffer[T]
}


object AlbumsRepository extends IBaseRepository[Album] {
  val albums = CsvReaderExample.main("albums.csv")
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

object TracksRepository extends IBaseRepository[Track]{
  val tracks = CsvReaderExample.main("tracks.csv")
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

object ArtistRepository extends IBaseRepository[Artist]{
  val artists = CsvReaderExample.main("artists.csv")
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

AlbumsRepository.getAll().foreach(println)

TracksRepository.getAll().foreach(println)

ArtistsRepository.getAll().foreach(println)



