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
      test("getAlbumById, the name should be R.O.O.T.S. (Route of Overcoming the Struggle)") {
        val album = AlbumRepository.getById("2vBLKFrI1rZqB7VtGxcsR5")
        assertTrue(album.get.name == "R.O.O.T.S. (Route of Overcoming the Struggle)")
      },
      test("getTrackById, 2JzZzZUQj3Qff7wapcbKjc")(
        assert(TrackRepository.getById("2JzZzZUQj3Qff7wapcbKjc"))(isSome)
      )
  )
}
