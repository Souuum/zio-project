package repositories.interface
import scala.collection.mutable.ListBuffer
import java.io.IOException
import zio.ZIO

trait IBaseRepository[T] {
  val mutableList = ListBuffer[T]()

  def getAll(): ZIO[T, IOException, ListBuffer[T]]
  def getById(id: String): Option[T]
  def getAllByAscPopularity(): ZIO[T, IOException, ListBuffer[T]]
  def getAllByDescPopularity(): ZIO[T, IOException, ListBuffer[T]]
  def getAllAveragePopularityByGenre(): ZIO[T, IOException, ListBuffer[T]]
}
