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

  def main(filename: String): List[Array[String]] = {
    val path = getClass.getResource("").getPath.replaceAll("/", "\\\\")
    val decodedPath = URLDecoder.decode(path, "UTF-8")
    val pathString = decodedPath.toString
    val grandparentDirectory = pathString.split("\\\\").dropRight(3).mkString("\\\\")
    val ressourcesDirectory = grandparentDirectory + "\\\\src\\\\main\\\\ressources"
    println(ressourcesDirectory)
    val data = readCSV(ressourcesDirectory + "\\\\" + filename)
    val separatedArrays = data.map(_.mkString(",")).map(_.split(","))

    separatedArrays
  }
}

val List = CsvReaderExample.main("Albums.csv")
val List2 = CsvReaderExample.main("Artists.csv")
val List3 = CsvReaderExample.main("Tracks.csv")

println("List:")
List.foreach(array => println(array.mkString(", ")))

println("\nList2:")
List2.foreach(array => println(array.mkString(", ")))

println("\nList3:")
List3.foreach(array => println(array.mkString(", ")))






