package batchs

import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*
import sttp.client3.*
import sttp.client3.asynchttpclient.future.AsyncHttpClientFutureBackend

import scala.concurrent.Await
import scala.concurrent.duration.*


case class Token(access_token: String, token_type: String, expires_in: Int)

class APICalls {

  private var TOKEN : String = _

  def getTOKEN: String = TOKEN;

  // FIX TOKEN FORMAT
  private def extractTokenFromString(responseBody: String): String = {
    if (responseBody.startsWith("Right(") && responseBody.endsWith(")")) {
      val jsonStr = responseBody.substring(6, responseBody.length - 1) // Extract the JSON string
      decode[Token](jsonStr).fold(
        error => s"Failed to parse JSON: $error",
        token => token.access_token
      )
    } else {
      "Unexpected response format"
    }
  }

  //GET TOKEN
  def extractToken() : Unit = {
    val backend = AsyncHttpClientFutureBackend()

    val requestToken = basicRequest
      .post(uri"https://accounts.spotify.com/api/token?grant_type=client_credentials&client_id=f637917e49654b379e297788669f4e33&client_secret=ae5f3214d0ec483386df22dc6b49486a")
      .header("Content-Type", "application/x-www-form-urlencoded")

    val response = Await.result(backend.send(requestToken), 5.seconds)

    TOKEN = extractTokenFromString(response.body.toString)
    backend.close()
  }


  // FIX Content
  def extractContent(responseBody: String): String = {
    if (responseBody.startsWith("Right(") && responseBody.endsWith(")")) {
      val jsonStr = responseBody.substring(6, responseBody.length - 1) // Extract the JSON string
      jsonStr
    } else {
      // Print an error message and return an indication of the unexpected format
      println("Unexpected response format")
      "Error: Unexpected response format"
    }
  }

  // GET ALL TRACKS
  def writeTracksInCSV(): Unit = {
    val backend = AsyncHttpClientFutureBackend()

    var request = basicRequest
      .get(uri"https://api.spotify.com/v1/tracks?ids=7ouMYWpwJ422jRcDASZB7P,4VqPOruhp5EdPBeR92t6lQ,2takcwOaAZWiXQijPHIx7B,5Ravsw8TmHN5wBiYPPYUFw,3hlksXnvbKogFdPbpO9vel,4uUG5RXrOk84mYEfFvj3cK,6ocbgoVGwYJhOv1GgI9NsF,1vELwZM3uBYWd8HrtddD4l,3Uyt0WO3wOopnUBCe9BaXl,1XGmzt0PVuFgQYYnV2It7A,2JzZzZUQj3Qff7wapcbKjc,56oGoEjA9eTZYgsttEFKY3,7lKXXZnos1Jm3V1raTzibi,2XHzzp1j4IfTNp1FTn7YFg,2YSzYUF3jWqb9YP9VXmpjE,4OMJGnvZfDvsePyCwRGO7X,4P76CEIXrrWT2cgS1YrTMr,3E7dfMvvCLUddWissuqMwr,2CEgGE6aESpnmtfiZwYlbV,7mDCg8XTEyDNPzC5Jdt7mu,2srTtSrzY4n10C7abVTrBm,3bC1ahPIYt1btJzSSEyyrF,0U10zFw4GlBacOy9VDGfGL,3zHq9ouUJQFQRf3cm1rRLu,2hrUO4drrO63i7FYbCLBl2,65uoaqX5qcjXZRheAj1qQT,5O2P9iiztwhomNh8xkR9lJ,4lLtanYk6tkMvooU0tWzG8,1BxfuPKGuaTgP7aM0Bbdwr,0TwBtDAWpkpM3srywFVOV5")
      .header("Authorization", "Bearer " + TOKEN)

    var response = Await.result(backend.send(request), 5.seconds)

    //Transform into JSON
    val jsonTracks = parse(extractContent(response.body.toString));

    val TracksWriterCSV: WriterCSV = new WriterCSV

    TracksWriterCSV.open("Tracks.csv")

    var entries = Array("id", "name", "popularity", "explicit", "external_urls", "id_album", "id_artists")

    TracksWriterCSV.writeInCSV(entries)

    // Extract information for each track in the array
    val trackArray: Vector[Json] = jsonTracks.flatMap(_.hcursor.downField("tracks").as[Vector[Json]]).getOrElse(Vector.empty)

    trackArray.foreach { trackJson =>
      val id: Option[String] = trackJson.hcursor.downField("id").as[String].toOption
      val name: Option[String] = trackJson.hcursor.downField("name").as[String].toOption
      val popularity: Option[Int] = trackJson.hcursor.downField("popularity").as[Int].toOption
      val explicit: Option[Boolean] = trackJson.hcursor.downField("explicit").as[Boolean].toOption
      val external_urls: Option[String] = trackJson.hcursor.downField("external_urls").as[Map[String, String]].toOption.flatMap(_.get("spotify"))
      val id_album: Option[String] = trackJson.hcursor.downField("album").downField("id").as[String].toOption
      //id_artists
      val artistsArray: Vector[Json] = trackJson.hcursor.downField("artists").as[Vector[Json]].getOrElse(Vector.empty)
      val id_artists: String = artistsArray.flatMap(_.hcursor.downField("id").as[String].toOption).mkString(" ")

      val entries: Array[String] = Array(id.getOrElse(""), name.getOrElse(""), popularity.map(_.toString).getOrElse(""), explicit.map(_.toString).getOrElse(""), external_urls.getOrElse(""), id_album.getOrElse(""), id_artists)

      TracksWriterCSV.writeInCSV(entries)
    }

    TracksWriterCSV.close()
    backend.close()
  }

  // GET ALL TRACKS
  def writeArtistsInCSV() : Unit = {
    val backend = AsyncHttpClientFutureBackend()

    var request = basicRequest
      .get(uri"https://api.spotify.com/v1/artists?ids=7lMgpN1tEBQKpRoUMKB8iw,04gDigrS5kc9YWfZHwBETP,137W8MRPWKqSmrBGDBFSop,67hb7towEyKvt5Z8Bx306c,55Aa2cqylxrFIXC767Z865,3TVXtAsR1Inumwj472S9r4,6M2wZ9GZgrQXHCFfjv46we,5YGY8feqx7naU7z4HrwZM6,6MF9fzBmfXghAz953czmBC,0jnsk9HBra6NMjO2oANoPY,5pKCCKE2ajJHZ9KAiaK11H,7CajNmpbOovFoOoasH2HaY,2wY79sveU1sp5g7SokKOiI,0X2BH1fck6amBIoJhDVmmJ,6MDME20pz9RveH9rEXvrOM,1Xylc3o4UrD53lo9CvFvVg,0FEJqmeLRzsXj8hgcZaAyB,4AK6F7OLvEQ5QYCBNiQWHq,0du5cEVh5yTK9QJze8zA0C,6jJ0s89eD6GaHleKKya26X,06HL4z0CvFAxyc27GXpf02,6vWDO969PvNqNYHIOW5v0m")
      .header("Authorization", "Bearer " + TOKEN)

    var response = Await.result(backend.send(request), 5.seconds)


    //Transform into JSON
    val jsonArtists = parse(extractContent(response.body.toString));


    val ArtistsCSV = new WriterCSV()
    ArtistsCSV.open("Artists.csv")

    var entries = Array("id", "name", "genres", "popularity", "external_urls")
    ArtistsCSV.writeInCSV(entries)


    // Extract information for each track in the array
    val artistsArray: Vector[Json] = jsonArtists.flatMap(_.hcursor.downField("artists").as[Vector[Json]]).getOrElse(Vector.empty)

    artistsArray.foreach { trackJson =>
      val id: Option[String] = trackJson.hcursor.downField("id").as[String].toOption
      val name: Option[String] = trackJson.hcursor.downField("name").as[String].toOption
      val genres: Option[String] = trackJson.hcursor.downField("genres").as[List[String]].toOption.map(_.mkString(","))
      val popularity: Option[Int] = trackJson.hcursor.downField("popularity").as[Int].toOption
      val external_urls: Option[String] = trackJson.hcursor.downField("external_urls").as[Map[String, String]].toOption.flatMap(_.get("spotify"))

      val entries: Array[String] = Array(id.getOrElse(""), name.getOrElse(""), genres.getOrElse(""), popularity.map(_.toString).getOrElse(""), external_urls.getOrElse(""))

      ArtistsCSV.writeInCSV(entries);
    }

    ArtistsCSV.close()
    backend.close()

  }

  // GET ALL ALBUMS
  def writeAlbumsInCSV() :Unit = {
    val backend = AsyncHttpClientFutureBackend()

    var request = basicRequest
      .get(uri"https://api.spotify.com/v1/albums?ids=704GHNtZhEe9TBgleCNNGv,01sfgrNbnnPUEyz6GZYlt9,5DvJgsMLbaR1HmAI6VhfcQ,0TjgUvNEDN2PegfZVkoggi,0ubDm7S0h9Yzt0CvwmOhGf,2vBLKFrI1rZqB7VtGxcsR5,6VN62DL9qSYG53VGXfohOn,0dNt3MfPrvj6mHmajSuahw,7lPoGKpCGgdKFAxpudhAH5,1SrvubPy1Dg2BWJyTMcmgr,00EjgohJGEYfe4vP35LVOv,1r7XjAgjwlakmXC2GbPXjH,4gCNyS7pidfK3rKWhB3JOY,2sWX3HYnZjPZ9MrH6MFsBt,4PgleR09JVnm3zY1fW3XBA,6J84szYCnMfzEcvIcfWMFL,3jB9yFDwRe3KhtGnHXJntk,6FJxoadUE4JNVwWHghBwnb,5MQBzs5YlZlE28mD9yUItn")
      .header("Authorization", "Bearer " + TOKEN)

    var response = Await.result(backend.send(request), 5.seconds)

    //Transform into JSON
    val jsonAlbums = parse(extractContent(response.body.toString));

    val AlbumsCSV = new WriterCSV()
    AlbumsCSV.open("Albums.csv")

    var entries = Array("id", "name", "album_type", "total_tracks", "release_date", "popularity", "external_urls", "id_artists")
    AlbumsCSV.writeInCSV(entries)


    // Extract information for each track in the array
    val albumsArray: Vector[Json] = jsonAlbums.flatMap(_.hcursor.downField("albums").as[Vector[Json]]).getOrElse(Vector.empty)

    albumsArray.foreach { trackJson =>
      val id: Option[String] = trackJson.hcursor.downField("id").as[String].toOption
      val name: Option[String] = trackJson.hcursor.downField("name").as[String].toOption
      val album_type: Option[String] = trackJson.hcursor.downField("album_type").as[String].toOption
      val total_tracks: Option[Int] = trackJson.hcursor.downField("total_tracks").as[Int].toOption
      val release_date: Option[String] = trackJson.hcursor.downField("release_date").as[String].toOption
      val popularity: Option[Int] = trackJson.hcursor.downField("popularity").as[Int].toOption
      val external_urls: Option[String] = trackJson.hcursor.downField("external_urls").as[Map[String, String]].toOption.flatMap(_.get("spotify"))
      //id_artists
      val artistsArray: Vector[Json] = trackJson.hcursor.downField("tracks").downField("items").as[Vector[Json]].getOrElse(Vector.empty)
      val id_artists: String = artistsArray.flatMap(_.hcursor.downField("artists").downArray.downField("id").as[String].toOption).mkString(" ")


      val entries: Array[String] = Array(id.getOrElse(""), name.getOrElse(""), album_type.getOrElse(""), total_tracks.map(_.toString).getOrElse(""), release_date.getOrElse(""), popularity.map(_.toString).getOrElse(""), external_urls.getOrElse(""), id_artists)

      AlbumsCSV.writeInCSV(entries);
    }

    AlbumsCSV.close()
    backend.close()

  }

}


/*
Track:

5Ravsw8TmHN5wBiYPPYUFw
3hlksXnvbKogFdPbpO9vel
4uUG5RXrOk84mYEfFvj3cK
6ocbgoVGwYJhOv1GgI9NsF
1vELwZM3uBYWd8HrtddD4l
3Uyt0WO3wOopnUBCe9BaXl
1XGmzt0PVuFgQYYnV2It7A
2JzZzZUQj3Qff7wapcbKjc
56oGoEjA9eTZYgsttEFKY3
7lKXXZnos1Jm3V1raTzibi
2XHzzp1j4IfTNp1FTn7YFg
2YSzYUF3jWqb9YP9VXmpjE
4OMJGnvZfDvsePyCwRGO7X
4P76CEIXrrWT2cgS1YrTMr
3E7dfMvvCLUddWissuqMwr
2CEgGE6aESpnmtfiZwYlbV
7mDCg8XTEyDNPzC5Jdt7mu
2srTtSrzY4n10C7abVTrBm
3bC1ahPIYt1btJzSSEyyrF
0U10zFw4GlBacOy9VDGfGL
3zHq9ouUJQFQRf3cm1rRLu
2hrUO4drrO63i7FYbCLBl2
65uoaqX5qcjXZRheAj1qQT
5O2P9iiztwhomNh8xkR9lJ
4lLtanYk6tkMvooU0tWzG8
1BxfuPKGuaTgP7aM0Bbdwr
0TwBtDAWpkpM3srywFVOV5
 */

/*

Artists:

7lMgpN1tEBQKpRoUMKB8iw
04gDigrS5kc9YWfZHwBETP
137W8MRPWKqSmrBGDBFSop
67hb7towEyKvt5Z8Bx306c
55Aa2cqylxrFIXC767Z865
3TVXtAsR1Inumwj472S9r4
6M2wZ9GZgrQXHCFfjv46we
5YGY8feqx7naU7z4HrwZM6
6MF9fzBmfXghAz953czmBC
0jnsk9HBra6NMjO2oANoPY
5pKCCKE2ajJHZ9KAiaK11H
7CajNmpbOovFoOoasH2HaY
2wY79sveU1sp5g7SokKOiI
0X2BH1fck6amBIoJhDVmmJ
6MDME20pz9RveH9rEXvrOM
1Xylc3o4UrD53lo9CvFvVg
0FEJqmeLRzsXj8hgcZaAyB
4AK6F7OLvEQ5QYCBNiQWHq
0du5cEVh5yTK9QJze8zA0C
6jJ0s89eD6GaHleKKya26X
06HL4z0CvFAxyc27GXpf02
6vWDO969PvNqNYHIOW5v0m
 */

/*
Album:

704GHNtZhEe9TBgleCNNGv
6Pqlfg42Sc3ElNuyt5NUiK
01sfgrNbnnPUEyz6GZYlt9
5DvJgsMLbaR1HmAI6VhfcQ
0IuHVgAvbNDJnJepuSZ8Oz
0TjgUvNEDN2PegfZVkoggi
0ubDm7S0h9Yzt0CvwmOhGf
2vBLKFrI1rZqB7VtGxcsR5
5j1wrOAOm5KFd17pPiSvle
6VN62DL9qSYG53VGXfohOn
22UyygZceCIfoE0RhENgKx
0dNt3MfPrvj6mHmajSuahw
7lPoGKpCGgdKFAxpudhAH5
1SrvubPy1Dg2BWJyTMcmgr
00EjgohJGEYfe4vP35LVOv
1r7XjAgjwlakmXC2GbPXjH
4gCNyS7pidfK3rKWhB3JOY
2sWX3HYnZjPZ9MrH6MFsBt
4PgleR09JVnm3zY1fW3XBA
6J84szYCnMfzEcvIcfWMFL
3jB9yFDwRe3KhtGnHXJntk
6FJxoadUE4JNVwWHghBwnb
1NAmidJlEaVgA3MpcPFYGq
5MQBzs5YlZlE28mD9yUItn
7dK54iZuOxXFarGhXwEXfF
 */
