package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompanies(
    @SerialName("id") val id: Int? = null,
    @SerialName("logo_path") val logo_path: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("origin_country") val origin_country: String? = null
)