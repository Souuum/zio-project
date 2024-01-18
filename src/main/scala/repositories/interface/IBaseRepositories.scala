package repositories.interface
import scala.collection.mutable.ListBuffer

trait IBaseRepository[T] {
  val mutableList = ListBuffer[T]()

  def getAll(): ListBuffer[T]
  def getById(id: String): Option[T]
  def getAllByAscPopularity(): ListBuffer[T]
  def getAllByDescPopularity(): ListBuffer[T]
  def getAllAveragePopularityByGenre(): ListBuffer[T]
}
