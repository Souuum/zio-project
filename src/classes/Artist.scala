// Classe qui représente un artiste

case class Artist(
    artistId: String,
    externalUrls: String,
    name: String,
    popularity: List[Popularity]
)
