package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguages(
    @SerialName("iso_639_1") val iso_639_1: String? = null,
    @SerialName("name") val name: String? = null
)