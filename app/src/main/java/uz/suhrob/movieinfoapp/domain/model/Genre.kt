package uz.suhrob.movieinfoapp.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Int,
    val name: String
): Parcelable

val defaultGenre = Genre(-1, "All")