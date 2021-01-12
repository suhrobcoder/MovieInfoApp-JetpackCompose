package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.suhrob.movieinfoapp.data.remote.model.MovieReviewDto

@Serializable
data class MovieReviewResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("results") val results: List<MovieReviewDto> = listOf(),
    @SerialName("total_pages") val totalPages: Int? = null,
    @SerialName("total_results") val totalResults: Int? = null,
)