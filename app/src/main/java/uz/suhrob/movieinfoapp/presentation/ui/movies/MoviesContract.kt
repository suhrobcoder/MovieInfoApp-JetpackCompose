package uz.suhrob.movieinfoapp.presentation.ui.movies

import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.base.UiEffect
import uz.suhrob.movieinfoapp.presentation.base.UiEvent
import uz.suhrob.movieinfoapp.presentation.base.UiState as BaseUiState
import uz.suhrob.movieinfoapp.presentation.components.Category

data class MoviesState(
    val selectedCategory: Category = Category.entries.first(),
    val categories: List<Category> = Category.entries,
    val selectedGenre: Genre = defaultGenre,
    val genres: List<Genre> = listOf(),
    val movies: List<Movie> = listOf(),
    val uiState: UiState = UiState.success,
) : BaseUiState

sealed interface MoviesEvent : UiEvent {
    data class ChangeCategory(val category: Category) : MoviesEvent
    data object LoadGenres : MoviesEvent
    data object LoadMovies : MoviesEvent
    data object NextPage : MoviesEvent
    data class SelectGenre(val genre: Genre) : MoviesEvent
    data class MovieClicked(val movie: Movie) : MoviesEvent
}

sealed interface MoviesEffect : UiEffect {
    data class NavigateToDetails(val movie: Movie) : MoviesEffect
}
