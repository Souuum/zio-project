package spotify.entities

case class ExternalId(
                    isrc: Option[String],
                    ean: Option[String],
                    upc: Option[String]
                     )
