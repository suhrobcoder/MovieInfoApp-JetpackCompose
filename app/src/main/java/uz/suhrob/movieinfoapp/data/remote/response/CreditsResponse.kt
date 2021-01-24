package uz.suhrob.movieinfoapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import uz.suhrob.movieinfoapp.data.remote.model.CastDto

@Serializable
data class CreditsResponse(
    @SerialName("id") val id: Int,
    @SerialName("cast") val cast: List<CastDto>
)