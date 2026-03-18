package uz.suhrob.movieinfoapp.presentation.ui.search

import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.base.UiEffect
import uz.suhrob.movieinfoapp.presentation.base.UiEvent
import uz.suhrob.movieinfoapp.presentation.base.UiState as BaseUiState

data class SearchState(
    val query: String = "",
    val movies: List<Movie> = listOf(),
    val uiState: UiState = UiState.success,
) : BaseUiState

sealed interface SearchEvent : UiEvent {
    data class QueryChange(val query: String) : SearchEvent
    data class MovieClick(val movie: Movie) : SearchEvent
    data object ExecuteSearch : SearchEvent
}

sealed interface SearchEffect : UiEffect {
    data class NavigateToDetails(val movie: Movie) : SearchEffect
}
