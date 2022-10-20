package uz.suhrob.movieinfoapp.presentation.ui.details

sealed interface DetailsEvent {
    object LoadMovie : DetailsEvent
    object LoadVideos : DetailsEvent
    object LoadCast : DetailsEvent
    object LoadReviews : DetailsEvent
    object LikeClick : DetailsEvent
    object SubmitRating : DetailsEvent
    data class SetRating(val rating: Int) : DetailsEvent
    object ShowDialog : DetailsEvent
    object CloseDialog : DetailsEvent
    object NavigateBack : DetailsEvent
}