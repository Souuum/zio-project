import batchs.APICalls
import zio.*
import zio.Console.*
import repositories.implementation.*

import scala.util.control.Breaks.break

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
      _ <- ZIO.foreach(0 until 5000) { a =>
        println("------------------------------------------")
        println("What would you like to do ?")
        println("1. See the top 10 artists ordered by popularity ascending")
        println("2. See the top 10 artists ordered by popularity descending")
        println("3. See the artists from top 10 by musical genre")
        println("4. See the top 10 albums ordered by popularity ascending")
        println("5. See the top 10 albums ordered by popularity descending")
        println("6. See the top 10 albums ordered by date ascending")
        println("7. See the top 10 albums ordered by date descending")
        println("8. See the top 10 tracks ordered by popularity ascending")
        println("9. See the top 10 tracks ordered by popularity descending")
        println("10. Quit")

        val answer = scala.io.StdIn.readLine()
        val answerInt = answer.toInt
        answerInt match {
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
            println("Goodbye!")
            break()
          case _ =>
            printLine("Please enter a valid number")
        }
      }


    } yield ()
  }

}
