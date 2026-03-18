package uz.suhrob.movieinfoapp.presentation.ui.details

import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.presentation.base.UiEffect
import uz.suhrob.movieinfoapp.presentation.base.UiEvent
import uz.suhrob.movieinfoapp.presentation.base.UiState
import uz.suhrob.movieinfoapp.presentation.components.MAX_RATING

data class DetailsState(
    val movie: Movie? = null,
    val videos: List<Video> = listOf(),
    val reviews: List<Review> = listOf(),
    val cast: List<Cast> = listOf(),
    val liked: Boolean = false,
    val showDialog: Boolean = false,
    val dialogRating: Int = MAX_RATING,
) : UiState

sealed interface DetailsEvent : UiEvent {
    data object LoadMovie : DetailsEvent
    data object LoadVideos : DetailsEvent
    data object LoadCast : DetailsEvent
    data object LoadReviews : DetailsEvent
    data object LikeClick : DetailsEvent
    data object SubmitRating : DetailsEvent
    data class SetRating(val rating: Int) : DetailsEvent
    data object ShowDialog : DetailsEvent
    data object CloseDialog : DetailsEvent
    data object NavigateBack : DetailsEvent
}

sealed interface DetailsEffect : UiEffect {
    data object NavigateBack : DetailsEffect
    data class ShowSnackbar(val message: String) : DetailsEffect
}
