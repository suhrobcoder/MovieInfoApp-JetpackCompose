package uz.suhrob.movieinfoapp.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val genres: List<Genre>,
    val releaseDate: String?,
    val video: Boolean,
    val voteCount: Int,
    val voteAverage: Double
)