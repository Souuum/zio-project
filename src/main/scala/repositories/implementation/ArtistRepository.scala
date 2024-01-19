package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Artist
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import batchs.CsvReaderBatch.readCSV
import zio.stream.ZStream
import zio.stream.ZSink

object ArtistRepository {

  val artists = CsvReaderBatch.beginRead("Artists.csv").tail
  val artistsMutableList: ListBuffer[Artist] = ListBuffer()
  for (artist <- artists) {
    val artistToAdd = artist match {
      case List(id, name, genreList, popularity, url) =>
        val genres = genreList.split(" ").map(_.trim).toList
        val popInt = popularity.toInt
        Artist(id, name, genres, popInt, url)

      case _ =>
        throw new IllegalArgumentException("Invalid list format")
    }
    artistsMutableList += artistToAdd
  }

  val aSuccess: ZIO[Any, IOException, ListBuffer[Artist]] =
    ZIO.succeed(artistsMutableList)

  val aStream: ZStream[Any, IOException, Artist] =
    ZStream.fromIterable(artistsMutableList)

  val getAll: ZSink[Any, Nothing, Artist, IOException, Chunk[Artist]] =
    ZSink.collectAll[Artist]

  val orderedByPopularityASC
      : ZSink[Any, Nothing, Artist, IOException, Chunk[Artist]] =
    getAll.map(_.sortWith(_.popularity < _.popularity))

  val getArtistOrderedByPopularityASC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityASC)
      .map(
        _.sortWith(_.popularity < _.popularity).map(artist =>
          (artist.name, artist.popularity)
        )
      )

  val orderedByPopularityDESC
      : ZSink[Any, Nothing, Artist, IOException, Chunk[Artist]] =
    getAll.map(_.sortWith(_.popularity > _.popularity))

  val getArtistOrderedByPopularityDESC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityDESC)
      .map(
        _.sortWith(_.popularity > _.popularity).map(artist =>
          (artist.name, artist.popularity)
        )
      )

  val selectByGenre: String => ZIO[Any, IOException, Chunk[(String, Int)]] =
    genre =>
      aStream
        .run(getAll)
        .map(
          _.filter(artist => artist.genres.contains(genre)).map(artist =>
            (artist.name, artist.popularity)
          )
        )

  def getPopASC = {
    for {
      _ <- printLine("Récupération des artistes")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des artistes par popularité ascendante")
      _ <- printLine("Veuillez patienter...")
      i <- getArtistOrderedByPopularityASC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getPopDESC = {
    for {
      _ <- printLine("Récupération des artistes")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des artistes par popularité descendante")
      _ <- printLine("Veuillez patienter...")
      i <- getArtistOrderedByPopularityDESC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getByGenre(genre: String) = {
    for {
      _ <- printLine("Récupération des artistes")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Récupération des artistes dans la catégorie " + genre)
      _ <- printLine("Veuillez patienter...")
      i <- selectByGenre(genre).map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

}
