package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.suhrob.movieinfoapp.data.remote.model.GenreDto

@Serializable
data class GenreResponse(
    @SerialName("genres")
    val genres: List<GenreDto> = listOf()
)