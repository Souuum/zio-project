// Classe qui représente un artiste
package entities
case class Artist(
    id: String,
    name: String,
    genres: List[String],
    popularity: Int,
    external_urls: String
)
