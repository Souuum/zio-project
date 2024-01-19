import zio._
import zio.http._

object HttpServer extends ZIOAppDefault {

  val spotifyPath = "https://api.spotify.com/v1/artists"
  val id = "0TnOYISbd1XYRBk9myaseg"
  val bearer =
    "BQA9ddKqyTx9t5xHp8iCVnsqpE0sQs3fDr3bY5aG0z5-1cJWpw81Ti5L-sI5JpJ8Tsp51u-80ppPzTz_wKh7IN7lGp7zuXsvxBKCY_Xtq06cyd0OiQ0"

  private object CounterHttpApp:
    def apply(): Http[Ref[Int], Nothing, Request, Response] =
      Http.collectZIO[Request] {
        case Method.GET -> spotifyPath / id =>
          ZIO.serviceWithZIO[Ref[Int]](cRef =>
            response(cRef.updateAndGet(_ + 1))
          )
        case Method.GET -> Root / "get" =>
          ZIO.serviceWithZIO[Ref[Int]](cRef => response(cRef.get))
        case Method.GET -> Root / "reset" =>
          ZIO.serviceWithZIO[Ref[Int]](cRef =>
            response(cRef.updateAndGet(_ => 0))
          )
      }

    private def response(counterUio: UIO[Int]) = counterUio
      .map(_.toString)
      .map(Response.text)

  def run: ZIO[Environment with ZIOAppArgs with Scope, Throwable, Any] =
    Server
      .serve(CounterHttpApp())
      .provide(
        ZLayer.fromZIO(Ref.make(0)),
        Server.defaultWithPort(8080)
      )
}
