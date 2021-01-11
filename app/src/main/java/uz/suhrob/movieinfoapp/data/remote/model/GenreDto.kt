package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("name") val name: String = ""
)