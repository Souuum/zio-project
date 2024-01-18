import com.opencsv.CSVReader
import scala.collection.JavaConverters._
import java.nio.file.{Paths, Path}
import java.net.URLDecoder

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

val data = CsvReaderExample.main("albums.csv")
println("\nData:")
println(data)



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

object AlbumRepository {
  val albums = ???

  def getAlbums(): List[Album] = albums

  def getAlbumById(id: String): Option[Album] = getAlbums().find(_.id == id)
}

val albums = AlbumRepository.getAlbums()
println("\nAlbums:")
println(albums)






