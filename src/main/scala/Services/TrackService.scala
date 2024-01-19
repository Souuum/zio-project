package services
import entities._
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import repositories.implementation.TrackRepository

object TrackService {

  def callGetAllTracks(): ZIO[Track, IOException, ListBuffer[Track]] = {
    return TrackRepository.getAll()
  }

  def callGetTrackById(id: String): Option[Track] = {
    return TrackRepository.getById(id)
  }
//trie les tracks par popularité
  def callSortTracksPopularityASC()
      : ZIO[Track, IOException, ListBuffer[Track]] = {
    return TrackRepository.getAllByAscPopularity()
  }

  def callSortTracksPopularityDESC()
      : ZIO[Track, IOException, ListBuffer[Track]] = {
    return TrackRepository.getAllByDescPopularity()
  }

}
