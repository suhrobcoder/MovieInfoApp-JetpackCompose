package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.suhrob.movieinfoapp.data.remote.model.MovieDto

@Serializable
data class MovieListResponse(
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<MovieDto> = listOf(),
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null,
)