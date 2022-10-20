package uz.suhrob.movieinfoapp.presentation.ui.details

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.presentation.components.MAX_RATING

interface Details {

    val state: Value<DetailsState>

    val dialogState: Value<DialogState>

    val snackBarFlow: Flow<String>

    fun sendEvent(event: DetailsEvent)

    data class DetailsState(
        val movie: Movie,
        val videos: List<Video> = listOf(),
        val reviews: List<Review> = listOf(),
        val cast: List<Cast> = listOf(),
        val liked: Boolean = false,
    )

    data class DialogState(
        val show: Boolean = false,
        val rating: Int = MAX_RATING,
    )
}