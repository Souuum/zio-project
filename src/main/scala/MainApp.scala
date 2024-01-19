import batchs.APICalls
import zio.*
import zio.Console.*
import repositories.implementation._

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic =
    for {
      _ <- printLine("Hello! Welcome to the Spotify API")
      _ <- printLine("Please wait while we are fetching the data")
      _ <- printLine("Data fetched!")
      _ <- ArtistRepository.getPopASC
      _ <- ArtistRepository.getPopDESC
      _ <- ArtistRepository.getByGenre("pop")
      _ <- ArtistRepository.getByGenre("rock")
      _ <- ArtistRepository.getByGenre("rap")
      _ <- ArtistRepository.getByGenre("hip hop")
      _ <- Console.printLine("------------------------------------------")
      _ <- AlbumRepository.getPopASC
      _ <- AlbumRepository.getPopDESC
      _ <- AlbumRepository.getDateASC
      _ <- AlbumRepository.getDateDESC
      _ <- Console.printLine("------------------------------------------")
      _ <- TrackRepository.getPopASC
      _ <- TrackRepository.getPopDESC

    } yield ()

  // Making the API Calls of spotify + writing them in a CSV
  val apiCalls: APICalls = new APICalls()

  apiCalls.extractToken()

  apiCalls.writeTracksInCSV();
  apiCalls.writeAlbumsInCSV()
  apiCalls.writeArtistsInCSV()
}
