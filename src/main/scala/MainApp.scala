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


/*
Track:

5Ravsw8TmHN5wBiYPPYUFw
3hlksXnvbKogFdPbpO9vel
4uUG5RXrOk84mYEfFvj3cK
6ocbgoVGwYJhOv1GgI9NsF
1vELwZM3uBYWd8HrtddD4l
3Uyt0WO3wOopnUBCe9BaXl
1XGmzt0PVuFgQYYnV2It7A
2JzZzZUQj3Qff7wapcbKjc
56oGoEjA9eTZYgsttEFKY3
7lKXXZnos1Jm3V1raTzibi
2XHzzp1j4IfTNp1FTn7YFg
2YSzYUF3jWqb9YP9VXmpjE
4OMJGnvZfDvsePyCwRGO7X
4P76CEIXrrWT2cgS1YrTMr
3E7dfMvvCLUddWissuqMwr
2CEgGE6aESpnmtfiZwYlbV
7mDCg8XTEyDNPzC5Jdt7mu
2srTtSrzY4n10C7abVTrBm
3bC1ahPIYt1btJzSSEyyrF
0U10zFw4GlBacOy9VDGfGL
3zHq9ouUJQFQRf3cm1rRLu
2hrUO4drrO63i7FYbCLBl2
65uoaqX5qcjXZRheAj1qQT
5O2P9iiztwhomNh8xkR9lJ
4lLtanYk6tkMvooU0tWzG8
1BxfuPKGuaTgP7aM0Bbdwr
0TwBtDAWpkpM3srywFVOV5
 */

/*

Artists:

7lMgpN1tEBQKpRoUMKB8iw
04gDigrS5kc9YWfZHwBETP
137W8MRPWKqSmrBGDBFSop
67hb7towEyKvt5Z8Bx306c
55Aa2cqylxrFIXC767Z865
3TVXtAsR1Inumwj472S9r4
6M2wZ9GZgrQXHCFfjv46we
5YGY8feqx7naU7z4HrwZM6
6MF9fzBmfXghAz953czmBC
0jnsk9HBra6NMjO2oANoPY
5pKCCKE2ajJHZ9KAiaK11H
7CajNmpbOovFoOoasH2HaY
2wY79sveU1sp5g7SokKOiI
0X2BH1fck6amBIoJhDVmmJ
6MDME20pz9RveH9rEXvrOM
1Xylc3o4UrD53lo9CvFvVg
0FEJqmeLRzsXj8hgcZaAyB
4AK6F7OLvEQ5QYCBNiQWHq
0du5cEVh5yTK9QJze8zA0C
6jJ0s89eD6GaHleKKya26X
06HL4z0CvFAxyc27GXpf02
6vWDO969PvNqNYHIOW5v0m
 */

/*
Album:

704GHNtZhEe9TBgleCNNGv
6Pqlfg42Sc3ElNuyt5NUiK
01sfgrNbnnPUEyz6GZYlt9
5DvJgsMLbaR1HmAI6VhfcQ
0IuHVgAvbNDJnJepuSZ8Oz
0TjgUvNEDN2PegfZVkoggi
0ubDm7S0h9Yzt0CvwmOhGf
2vBLKFrI1rZqB7VtGxcsR5
5j1wrOAOm5KFd17pPiSvle
6VN62DL9qSYG53VGXfohOn
22UyygZceCIfoE0RhENgKx
0dNt3MfPrvj6mHmajSuahw
7lPoGKpCGgdKFAxpudhAH5
1SrvubPy1Dg2BWJyTMcmgr
00EjgohJGEYfe4vP35LVOv
1r7XjAgjwlakmXC2GbPXjH
4gCNyS7pidfK3rKWhB3JOY
2sWX3HYnZjPZ9MrH6MFsBt
4PgleR09JVnm3zY1fW3XBA
6J84szYCnMfzEcvIcfWMFL
3jB9yFDwRe3KhtGnHXJntk
6FJxoadUE4JNVwWHghBwnb
1NAmidJlEaVgA3MpcPFYGq
5MQBzs5YlZlE28mD9yUItn
7dK54iZuOxXFarGhXwEXfF
 */

