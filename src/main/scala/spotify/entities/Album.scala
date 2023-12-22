package spotify.entities
import java.awt.Image

case class Album(
                  album_type: String,
                  total_tracks: Int,
                  available_markets: List[String],
                  external_urls: ExternalUrl,
                  href: String,
                  id: String,
                  images: List[Image],
                  name: String,
                  release_date: String,
                  release_date_precision: String,
                  restrictions: Restriction,
                  `type`: String,
                  artists: List[ArtistSimple],
                  tracks: TrackSimple,
                  copyrights: List[Copyright],
                  external_ids: ExternalId,
                  genres: List[String],
                  label: String,
                  popularity: Int,
                )
