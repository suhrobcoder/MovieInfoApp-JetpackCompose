package uz.suhrob.movieinfoapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("adult") val adult: Boolean? = null,
    @SerialName("backdrop_path") val backdrop_path: String? = null,
    @SerialName("budget") val budget: Int? = null,
    @SerialName("genres") val genres: List<GenreDto> = listOf(),
    @SerialName("genre_ids") val genreIds: List<Int> = listOf(),
    @SerialName("homepage") val homepage: String? = null,
    @SerialName("id") val id: Int = 0,
    @SerialName("imdb_id") val imdb_id: String? = null,
    @SerialName("original_language") val original_language: String? = null,
    @SerialName("original_title") val original_title: String? = null,
    @SerialName("overview") val overview: String? = null,
    @SerialName("popularity") val popularity: Double? = null,
    @SerialName("poster_path") val poster_path: String? = null,
    @SerialName("production_companies") val production_companies: List<ProductionCompanies> = listOf(),
    @SerialName("production_countries") val production_countries: List<ProductionCountries> = listOf(),
    @SerialName("release_date") val release_date: String? = null,
    @SerialName("revenue") val revenue: Long? = null,
    @SerialName("runtime") val runtime: Int? = null,
    @SerialName("spoken_languages") val spoken_languages: List<SpokenLanguages> = listOf(),
    @SerialName("status") val status: String? = null,
    @SerialName("tagline") val tagline: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("video") val video: Boolean? = null,
    @SerialName("vote_average") val vote_average: Double? = null,
    @SerialName("vote_count") val vote_count: Int? = null
)