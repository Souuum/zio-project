import batchs.APICalls
import zio.*
import zio.Console.*

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine("Hello! What is your name?")
      name <- readLine
      _ <- printLine(s"Hello, ${name}, welcome to ZIO!")
    } yield ()


  //Making the API Calls of spotify + writing them in a CSV
  val apiCalls: APICalls = new APICalls()

  apiCalls.extractToken()

  apiCalls.writeTracksInCSV();
  apiCalls.writeAlbumsInCSV()
  apiCalls.writeArtistsInCSV()
}
