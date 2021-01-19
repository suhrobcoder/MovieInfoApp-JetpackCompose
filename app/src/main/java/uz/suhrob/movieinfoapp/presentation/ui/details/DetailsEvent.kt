package uz.suhrob.movieinfoapp.presentation.ui.details

sealed class DetailsEvent {
    object LoadMovie: DetailsEvent()
    object LoadVideos: DetailsEvent()
    object LoadReviews: DetailsEvent()
    object LikeClick: DetailsEvent()
}