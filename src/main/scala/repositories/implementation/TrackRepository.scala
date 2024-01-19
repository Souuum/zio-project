package repositories.implementation
import batchs.CsvReaderBatch
import repositories.interface.IBaseRepository
import entities.Track
import zio.ZIO
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import zio.stream.ZSink
import zio.Chunk
import zio.stream.ZStream

object TrackRepository {
  val tracks = CsvReaderBatch.beginRead("Tracks.csv").tail
  val tracksMutableList: ListBuffer[Track] = ListBuffer()
  for (track <- tracks) {
    val trackToAdd = track match {
      case List(
            id,
            name,
            popularity,
            explicit,
            url,
            id_track,
            id_tracksList
          ) =>
        val id_tracks = id_tracksList.split(" ").map(_.trim).toList
        val popInt = popularity.toInt
        Track(id, name, popInt, explicit, url, id_track, id_tracks)
      case _ =>
        throw new IllegalArgumentException("Invalid list format")
    }
    tracksMutableList += trackToAdd

  }
  val aSuccess: ZIO[Any, IOException, ListBuffer[Track]] =
    ZIO.succeed(tracksMutableList)

  val aStream: ZStream[Any, IOException, Track] =
    ZStream.fromIterable(tracksMutableList)

  val getAll: ZSink[Any, Nothing, Track, IOException, Chunk[Track]] =
    ZSink.collectAll[Track]

  val orderedByPopularityASC
      : ZSink[Any, Nothing, Track, IOException, Chunk[Track]] =
    getAll.map(_.sortWith(_.popularity < _.popularity))

  val getTrackOrderedByPopularityASC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityASC)
      .map(
        _.sortWith(_.popularity < _.popularity).map(track =>
          (track.name, track.popularity)
        )
      )

  val orderedByPopularityDESC
      : ZSink[Any, Nothing, Track, IOException, Chunk[Track]] =
    getAll.map(_.sortWith(_.popularity > _.popularity))

  val getTrackOrderedByPopularityDESC
      : ZIO[Any, IOException, Chunk[(String, Int)]] =
    aStream
      .run(orderedByPopularityDESC)
      .map(
        _.sortWith(_.popularity > _.popularity).map(track =>
          (track.name, track.popularity)
        )
      )

  def getPopASC = {
    for {
      _ <- printLine("Récupération des tracks")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des tracks par popularité ascendante")
      _ <- printLine("Veuillez patienter...")
      i <- getTrackOrderedByPopularityASC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

  def getPopDESC = {
    for {
      _ <- printLine("Récupération des tracks")
      _ <- printLine("Veuillez patienter...")
      _ <- printLine("Récupération terminée !")
      _ <- printLine("Trie des tracks par popularité descendante")
      _ <- printLine("Veuillez patienter...")
      i <- getTrackOrderedByPopularityDESC.map(_.foreach(println))
      _ <- printLine("Trie terminé !")
    } yield i
  }

}
