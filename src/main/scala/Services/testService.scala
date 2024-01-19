import services.AlbumService
import services.ArtistService
import services.TrackService
import zio.Console._

object testService extends App {
  println("Hello World")

  val test = ArtistService.callSortArtistsPopularityASC()

  val test2 = ArtistService.test()
  Console.println(test2)
}
