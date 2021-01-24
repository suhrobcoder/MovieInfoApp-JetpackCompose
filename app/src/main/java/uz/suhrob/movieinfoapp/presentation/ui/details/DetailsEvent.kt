package uz.suhrob.movieinfoapp.presentation.ui.details

sealed class DetailsEvent {
    object LoadMovie: DetailsEvent()
    object LoadVideos: DetailsEvent()
    object LoadCast: DetailsEvent()
    object LoadReviews: DetailsEvent()
    object LikeClick: DetailsEvent()
}