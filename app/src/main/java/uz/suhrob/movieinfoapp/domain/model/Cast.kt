package uz.suhrob.movieinfoapp.domain.model

data class Cast(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val character: String
)