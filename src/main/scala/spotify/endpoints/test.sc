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

  def main(filename: String): List[String] = {
    val path = getClass.getResource("").getPath.replaceAll("/", "\\\\")
    val decodedPath = URLDecoder.decode(path, "UTF-8")
    val pathString = decodedPath.toString
    val grandparentDirectory = pathString.split("\\\\").dropRight(3).mkString("\\\\")
    val ressourcesDirectory = grandparentDirectory + "\\\\src\\\\main\\\\ressources"
    println(ressourcesDirectory)
    val data = readCSV(ressourcesDirectory + "\\\\" + filename)
    return data.map(_.mkString(","))
  }
}

CsvReaderExample.main("Albums.csv")






