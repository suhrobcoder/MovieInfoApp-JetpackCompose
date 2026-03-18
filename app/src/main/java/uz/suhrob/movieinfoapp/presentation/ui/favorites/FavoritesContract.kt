package uz.suhrob.movieinfoapp.presentation.ui.favorites

import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.base.UiEffect
import uz.suhrob.movieinfoapp.presentation.base.UiEvent
import uz.suhrob.movieinfoapp.presentation.base.UiState

data class FavoritesState(
    val movies: List<Movie> = listOf(),
) : UiState

sealed interface FavoritesEvent : UiEvent {
    data class MovieClick(val movie: Movie) : FavoritesEvent
}

sealed interface FavoritesEffect : UiEffect {
    data class NavigateToDetails(val movie: Movie) : FavoritesEffect
}
