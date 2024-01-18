package batchs
import scala.collection.JavaConverters._
import com.opencsv.CSVReader
import java.net.URLDecoder


object CsvReaderBatch {
  def readCSV(filename: String): List[List[String]] = {
    val reader = new CSVReader(new java.io.FileReader(filename))
    try {
      reader.readAll().asScala.map(_.toList).toList
    } finally {
      reader.close()
    }
  }

  def beginRead(filename: String): List[List[String]] = {
    val path = getClass.getResource("").getPath.replaceAll("/", "\\\\")
    val decodedPath = URLDecoder.decode(path, "UTF-8")
    val pathString = decodedPath.toString
    val grandparentDirectory = pathString.split("\\\\").dropRight(4).mkString("\\\\")
    val ressourcesDirectory = grandparentDirectory + "\\\\src\\\\main\\\\ressources"
    println(ressourcesDirectory)
    val data = readCSV(ressourcesDirectory + "\\\\" + filename)
    data
  }
}
