package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieVideoDto(
    @SerialName("id") val id: String? = null,
    @SerialName("iso_639_1") val iso_639_1: String? = null,
    @SerialName("iso_3166_1") val iso_3166_1: String? = null,
    @SerialName("key") val key: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("site") val site: String? = null,
    @SerialName("size") val size: Int? = null,
    @SerialName("type") val type: String? = null
)