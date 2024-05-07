package portfolio.jjs.domain.model

data class Movie(
    val movieId: String,
    val movieName: String,
    val characters: List<String>,
    val imageUrl: String,
    val summary: String,
    val time: String,
    val filmRatings: String
)