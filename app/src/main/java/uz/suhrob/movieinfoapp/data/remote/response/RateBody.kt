package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RateBody(
    @SerialName("value") val value: Int
)