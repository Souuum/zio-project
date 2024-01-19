import entities.Album
import scala.collection.mutable.ListBuffer
import repositories.implementation
import repositories.implementation.AlbumRepository

// val album: List[Album] = albumRepo.getAll()

val album: ListBuffer[Album] = AlbumRepository.getAll()

object TestWorksheet extends App {
  def main1(args: Array[String]): Unit = {
    println(AlbumService.sortAlbumsPopularityZIOStream(album))
  }
}

TestWorksheet.main1(Array())
