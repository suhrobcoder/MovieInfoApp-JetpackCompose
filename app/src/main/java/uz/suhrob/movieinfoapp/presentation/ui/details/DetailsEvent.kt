package uz.suhrob.movieinfoapp.presentation.ui.details

sealed interface DetailsEvent {
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