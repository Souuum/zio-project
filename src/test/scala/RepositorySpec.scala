import entities.Album
import zio.*
import zio.test.*
import zio.test.Assertion.*
import repositories.implementation.AlbumRepository
import repositories.implementation.TrackRepository
import repositories.implementation.ArtistRepository
import entities.Artist
import scala.collection.mutable.ListBuffer

import java.io.IOException

object RepositorySpec extends ZIOSpecDefault{
  def spec: Spec[Any, Any] =
    suite("RepositorySpec")(

      test("getAllArtistsByPopularity in ascending order") {
        for{
          artists <- ArtistRepository.getArtistOrderedByPopularityASC
          sorted = artists.sortBy(_._2)
        }yield assert(artists)(equalTo(sorted))
      }
  )
}
