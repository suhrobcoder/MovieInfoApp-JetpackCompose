package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.suhrob.movieinfoapp.data.remote.model.MovieVideoDto

@Serializable
data class MovieVideoResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("results") val results: List<MovieVideoDto> = listOf()
)