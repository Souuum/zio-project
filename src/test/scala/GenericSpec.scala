import zio.ZIO
import zio.test.{ZIOSpecDefault, assertTrue}

object ExampleSpec extends ZIOSpecDefault {

  def returnString(str: String): ZIO[Any, Nothing, String] =
    for {
      _ <- ZIO.unit
    } yield str
  override def spec = suite("TestingApplicationsExamplesSpec")(
    test("returnString correctly returns string") {
      val testString = "Hello World!"
      for {
        output <- returnString(testString)
      } yield assertTrue(output == testString)
    }
  )
}
