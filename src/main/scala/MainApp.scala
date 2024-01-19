import batchs.APICalls
import zio.*
import zio.Console.*
import repositories.implementation._

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  // Making the API Calls of spotify + writing them in a CSV
  val apiCalls: APICalls = new APICalls()
  val myAppLogic = {

    // Making the API Calls of spotify + writing them in a CSV
    val apiCalls: APICalls = new APICalls()

    apiCalls.extractToken()

    apiCalls.writeTracksInCSV()
    apiCalls.writeAlbumsInCSV()
    apiCalls.writeArtistsInCSV()

    for {
      _ <- printLine("Hello! Welcome to the Spotify API")
      _ <- printLine("Please wait while we are fetching the data")
      _ <- printLine("Data fetched!")
      _ <- printLine("------------------------------------------")
      _ <- printLine("What would you like to do ?")
      _ <- printLine(
        "1. See the top 10 artists ordered by popularity ascending"
      )
      _ <- printLine(
        "2. See the top 10 artists ordered by popularity descending"
      )
      _ <- printLine("3. See the artists from top 10 by musical genre")
      _ <- printLine("4. See the top 10 albums ordered by popularity ascending")
      _ <- printLine(
        "5. See the top 10 albums ordered by popularity descending"
      )
      _ <- printLine("6. See the top 10 albums ordered by date ascending")
      _ <- printLine("7. See the top 10 albums ordered by date descending")
      _ <- printLine("8. See the top 10 tracks ordered by popularity ascending")
      _ <- printLine(
        "9. See the top 10 tracks ordered by popularity descending"
      )
      _ <- printLine("10. Quit")

      answer <- readLine
      answerInt = answer.toInt
      _ <- answerInt match {
        case 1 =>
          ArtistRepository.getPopASC
        case 2 =>
          ArtistRepository.getPopDESC
        case 3 => {
          for {
            _ <- printLine("Please enter a musical genre")
            genre <- readLine
            _ <- ArtistRepository.getByGenre(genre)
          } yield ()
        }
        case 4 =>
          AlbumRepository.getPopASC
        case 5 =>
          AlbumRepository.getPopDESC
        case 6 =>
          AlbumRepository.getDateASC
        case 7 =>
          AlbumRepository.getDateDESC
        case 8 =>
          TrackRepository.getPopASC
        case 9 =>
          TrackRepository.getPopDESC
        case 10 =>
          printLine("Goodbye!")
        case _ =>
          printLine("Please enter a valid number")
      }

    } yield ()
  }

}
