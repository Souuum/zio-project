// Classe représentant la popularité d'un artiste, d'un album ou d'un morceau de musique
// Composée d'un score sur 100 et d'une date de mesure

case class Popularity(
    score: Int,
    date: java.time.LocalDate
)
