package uz.suhrob.movieinfoapp.domain.model

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val genres: List<Genre>,
    val releaseDate: String?,
    val runtime: Int?,
    val video: Boolean,
    val voteCount: Int,
    val voteAverage: Double
): Parcelable {
    fun getMovieReleaseYear(): String {
        return releaseDate?.let {
            it.split("-")[0]
        } ?: ""
    }
}