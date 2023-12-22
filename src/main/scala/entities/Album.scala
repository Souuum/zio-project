// Classe qui représente un album

case class Album(
    albumId: String,
    name: String,
    externalUrls: String,
    releaseDate: java.time.LocalDate,
    genres: List[Genre],
    artists: List[Artist],
    tracks: List[Track],
    popularity: List[Popularity]
)
