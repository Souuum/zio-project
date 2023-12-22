package spotify.entities
case class ArtistSimple(
                         external_urls: ExternalUrl,
                         href: String,
                         id: String,
                         name: String,
                         `type`: String,
                         uri: String
                       )