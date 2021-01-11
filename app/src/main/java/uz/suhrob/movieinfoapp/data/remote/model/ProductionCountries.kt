package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountries(
    @SerialName("iso_3166_1") val iso_3166_1: String? = null,
    @SerialName("name") val name: String? = null
)