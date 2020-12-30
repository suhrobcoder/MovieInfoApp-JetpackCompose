package uz.suhrob.movieinfoapp.domain.model

data class Review(
    val id: String,
    val content: String,
    val createdAt: String,
    val authorName: String,
    val avatarPath: String
)