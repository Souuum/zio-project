// Classe qui représente un album
package entities

case class Album(
    id: String,
    name: String,
    album_type: String,
    total_tracks: String,
    release_date: String,
    popularity: Int,
    external_urls: String,
    id_artists: List[String],
    id_tracks: List[String]
)
