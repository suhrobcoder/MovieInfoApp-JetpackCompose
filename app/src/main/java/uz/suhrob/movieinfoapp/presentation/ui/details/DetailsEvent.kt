package uz.suhrob.movieinfoapp.presentation.ui.details

sealed class DetailsEvent {
    object LoadMovie: DetailsEvent()
    object LoadVideos: DetailsEvent()
    object LoadCast: DetailsEvent()
    object LoadReviews: DetailsEvent()
    object LikeClick: DetailsEvent()
    data class SubmitRating(val rating: Int): DetailsEvent()
    object ShowDialog: DetailsEvent()
    object CloseDialog: DetailsEvent()
}