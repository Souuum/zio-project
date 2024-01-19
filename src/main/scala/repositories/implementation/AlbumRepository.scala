package repositories.implementation

import batchs.CsvReaderBatch
import scala.collection.mutable.ListBuffer
import repositories.interface.IBaseRepository
import entities.Album
import zio.ZIO
import zio.Console._
import java.io.IOException
import zio.stream.ZSink
import zio.stream.ZStream
import zio.Chunk
object AlbumRepository {
  val albums = CsvReaderBatch.beginRead("Albums.csv").tail
  val albumsMutableList: ListBuffer[Album] = ListBuffer()
  for (album <- albums) {
    val albumAlbumoAdd = album match {
      case List(
            id,
            name,
            album_type,
            total_tracks,
            release_date,
            popularity,
            external_urls,
            id_albumsList,
            id_tracksList
          ) =>
        val id_albums = id_albumsList.split(" ").map(_.trim).toList
        val id_tracks = id_tracksList.split(" ").map(_.trim).toList
        val popInt = popularity.toInt
        Album(
          id,
          name,
          album_type,
          total_tracks,
          release_date,
          popInt,
          external_urls,
          id_albums,
          id_tracks
        )
    }
    albumsMutableList += albumAlbumoAdd
  }

  val aSuccess: ZIO[Any, IOException, ListBuffer[Album]] =
    ZIO.succeed(albumsMutableList)

  val aStream: ZStream[Any, IOException, Album] =
    ZStream.fromIterable(albumsMutableList)

  val getAll: ZSink[Any, Nothing, Album, IOException, Chunk[Album]] =
    ZSink.collectAll[Album]

  val orderedByPopularityASC
      : ZSink[Any, Nothing, Album, IOException, Chunk[Album]] =
    getAll.map(_.sortWith(_.popularity < _.popularity))

  val getAlbumOrderedByPopularityASC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityASC)
      .map(
        _.sortWith(_.popularity < _.popularity).map(album =>
          (album.name, album.popularity)
        )
      )

  val orderedByPopularityDESC
      : ZSink[Any, Nothing, Album, IOException, Chunk[Album]] =
    getAll.map(_.sortWith(_.popularity > _.popularity))

  val getAlbumOrderedByPopularityDESC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityDESC)
      .map(
        _.sortWith(_.popularity > _.popularity).map(album =>
          (album.name, album.popularity)
        )
      )

  val orderedByDateASC: ZSink[Any, Nothing, Album, IOException, Chunk[Album]] =
    getAll.map(_.sortWith(_.release_date < _.release_date))

  val getAlbumOrderedByDateASC: ZIO[Any, IOException, Chunk[(String, String)]] =
    aStream
      .run(orderedByDateASC)
      .map(
        _.sortWith(_.release_date < _.release_date).map(album =>
          (album.name, album.release_date)
        )
      )

  val orderedByDateDESC: ZSink[Any, Nothing, Album, IOException, Chunk[Album]] =
    getAll.map(_.sortWith(_.release_date > _.release_date))

  val getAlbumOrderedByDateDESC
      : ZIO[Any, IOException, Chunk[(String, String)]] =
    aStream
      .run(orderedByDateDESC)
      .map(
        _.sortWith(_.release_date > _.release_date).map(album =>
          (album.name, album.release_date)
        )
      )

  def getPopASC = {
    for {
      _ <- printLine("Récupération des albums")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des albums par popularité ascendante")
      _ <- printLine("Veuillez patienter...")
      i <- getAlbumOrderedByPopularityASC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getPopDESC = {
    for {
      _ <- printLine("Récupération des albums")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des albums par popularité descendante")
      _ <- printLine("Veuillez patienter...")
      i <- getAlbumOrderedByPopularityDESC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getDateASC = {
    for {
      _ <- printLine("Récupération des albums")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des albums par date ascendante")
      _ <- printLine("Veuillez patienter...")
      i <- getAlbumOrderedByDateASC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getDateDESC = {
    for {
      _ <- printLine("Récupération des albums")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des albums par date descendante")
      _ <- printLine("Veuillez patienter...")
      i <- getAlbumOrderedByDateDESC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

}
