package uz.suhrob.movieinfoapp.domain.model

data class Genre(
    val id: Int,
    val name: String
)

val defaultGenre = Genre(-1, "All")