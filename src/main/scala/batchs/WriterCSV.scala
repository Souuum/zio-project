package batchs

import com.opencsv.CSVWriter

import java.io.{BufferedWriter, File, FileWriter}
import java.net.URLDecoder

class WriterCSV {

    // Making Directory
    private val path: String = getClass.getResource("").getPath.replaceAll("/", "\\\\")
    private val decodedPath: String = URLDecoder.decode(path, "UTF-8")
    private val grandparentDirectory: String = decodedPath.split("\\\\").dropRight(4).mkString("\\\\")
    private val resourcesDirectory: String = grandparentDirectory + "\\\\src\\\\main\\\\resources"

    private var tracksPath : String = _
    private var CSVFile : CSVWriter = _

    def writeInCSV(data: Array[String]): Unit = {
        CSVFile.writeNext(data)
    }

    def open(fileName: String): Unit = {
        CSVFile = new CSVWriter(new FileWriter(s"$resourcesDirectory/$fileName"))
    }

    def close(): Unit = {
        CSVFile.close()
    }
}
