package spotify.entities

case class Track(
                album : Album,
                artists : List[Artist],
                available_markets : List[String],
                disc_number : Int,
                duration_ms : Int,
                explicit : Boolean,
                external_ids : ExternalId,
                external_urls : ExternalUrl,
                href : String,
                id : String,
                is_playable : Boolean,
                linked_from : TrackLink,
                restrictions : Restriction,
                name : String,
                popularity : Int,
                preview_url : String,
                track_number : Int,
                `type` : String,
                uri : String,
                is_local : Boolean
                )
