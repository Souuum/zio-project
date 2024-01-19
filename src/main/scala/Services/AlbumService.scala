package services
import entities._
import zio._
import zio.Console._
import java.io.IOException
import scala.collection.mutable.ListBuffer
import repositories.implementation.AlbumRepository

//TODO
object AlbumService {

//trie les albums par popularité
  def callSortAlbumsPopularityASC()
      : ZIO[Album, IOException, ListBuffer[Album]] = {
    return AlbumRepository.getAllByAscPopularity()
  }

  def callSortAlbumsPopularityDESC()
      : ZIO[Album, IOException, ListBuffer[Album]] = {
    return AlbumRepository.getAllByDescPopularity()
  }

}
