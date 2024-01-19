import zio.ZIO
import zio.test.{ZIOSpecDefault, assertTrue}
import batchs.CsvReaderBatch
object ReasCsvSpec extends ZIOSpecDefault {
  override def spec = suite("TestingCsvReadingSpec")(
    test("Csv reading should return a list of lists of strings of List(List(\"1\", \"2\", \"3\"), List(\"4\", \"5\", \"6\")") {
      val csvRead = CsvReaderBatch.beginRead("test.csv")
      assertTrue(csvRead == List(List("1", "2", "3"), List("4", "5", "6")))
     }
  )
}
