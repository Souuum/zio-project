package spotify.entities

case class TrackSimple(
                        artists: List[ArtistSimple],
                        available_markets: List[String],
                        disc_number: Int,
                        duration_ms: Int,
                        explicit: Boolean,
                        external_urls: ExternalUrl,
                        href: String,
                        id: String,
                        is_playable: Option[Boolean],
                        linked_from: Option[TrackLink],
                        restrictions: Restriction,
                        name: String,
                        preview_url: String,
                        track_number: Int,
                        `type`: String,
                        uri: String,
                        is_local: Boolean
                      )
