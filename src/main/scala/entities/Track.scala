// Classe qui représente un morceau de musique
package entities
case class Track(
                  id : String,
                  name: String,
                  popularity: String,
                  explicit : String,
                  external_urls : String,
                  id_artists: List[String],
                  id_tracks: List[String]
                )
