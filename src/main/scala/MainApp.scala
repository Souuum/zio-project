import batchs.APICalls
import zio.*
import zio.Console.*

object MyApp extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic = {

    //Making the API Calls of spotify + writing them in a CSV
    val apiCalls: APICalls = new APICalls()

    apiCalls.extractToken()

    apiCalls.writeTracksInCSV()
    apiCalls.writeAlbumsInCSV()
    apiCalls.writeArtistsInCSV()

    for {
      _ <- printLine("Hello!\nYour App is ready to be used")
      _ <- printLine(menuInput())
    } yield ()
  }

  def menuInput(): Unit = {
    while(true) {
      println("Choose the right number")
      println("1. Tracks")
      println("2. Albums")
      println("3. Artists")
      println("0. Exit")
      val input: String = scala.io.StdIn.readLine()
      if (input == "0") {
        return false
      }
      else if (input == "1") {
        println("You selected Tracks")
        tracksInput()
      }
      else if (input == "2") {
        println("You selected Albums")
        albumsInput()
      }
      else if (input == "3") {
        println("You selected Artists")
        artistsInput()
      }
    }
  }

  //MODIFY FROM HERE
  def tracksInput(): Unit = {
    println()

    println("1. ...")
    println("2. ...")
    println("3. ...")
    println("0. Exit")

    while (true) {
      println("Choose the right number")
      val input: String = scala.io.StdIn.readLine()
      if (input == "0") {
        return false
      }
      else if (input == "1") {
        println("You selected Tracks1")
      }
      else if (input == "2") {
        println("You selected Albums2")
      }
      else if (input == "3") {
        println("You selected Artists3")
      }
      else if (input == "4") {
        println("You selected Tracks1")
      }
      else if (input == "5") {
        println("You selected Albums2")
      }
      else if (input == "6") {
        println("You selected Artists3")
      }
    }
  }

  def albumsInput(): Unit = {
    println()

    println("1. ...")
    println("2. ...")
    println("3. ...")
    println("0. Exit")

    while (true) {
      println("Choose the right number")
      val input: String = scala.io.StdIn.readLine()
      if (input == "0") {
        return false
      }
      else if (input == "1") {
        println("You selected Tracks1")
      }
      else if (input == "2") {
        println("You selected Albums2")
      }
      else if (input == "3") {
        println("You selected Artists3")
      }
      else if (input == "4") {
        println("You selected Tracks1")
      }
      else if (input == "5") {
        println("You selected Albums2")
      }
      else if (input == "6") {
        println("You selected Artists3")
      }
    }
  }

  def artistsInput(): Unit = {
    println()

    println("1. ...")
    println("2. ...")
    println("3. ...")
    println("0. Exit")

    while (true) {
      println("Choose the right number")
      val input: String = scala.io.StdIn.readLine()
      if (input == "0") {
        return false
      }
      else if (input == "1") {
        println("You selected Tracks1")
      }
      else if (input == "2") {
        println("You selected Albums2")
      }
      else if (input == "3") {
        println("You selected Artists3")
      }
      else if (input == "4") {
        println("You selected Tracks1")
      }
      else if (input == "5") {
        println("You selected Albums2")
      }
      else if (input == "6") {
        println("You selected Artists3")
      }
    }
  }
}

