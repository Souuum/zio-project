package spotify.entities

import java.awt.Image

case class Artist(
                 external_urls: ExternalUrl,
                 followers: Follower,
                  genres: List[String],
                  href: String,
                  id: String,
                  images: List[Image],
                 name: String,
                  popularity: Int,
                  `type`: String,
                  uri: String
                 )
