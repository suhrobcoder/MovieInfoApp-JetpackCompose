package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastDto(
    @SerialName("adult") val adult: Boolean,
    @SerialName("gender") val gender: Int?,
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("profile_path") val profilePath: String?,
    @SerialName("popularity") val popularity: Double,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("cast_id") val castId: Int,
    @SerialName("order") val order: Int
)