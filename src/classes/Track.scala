// Classe qui représente un morceau de musique

case class Track(
    trackId: String,
    name: String,
    externalUrls: String,
    popularity: List[Popularity]
)
