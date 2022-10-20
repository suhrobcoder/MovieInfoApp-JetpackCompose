package uz.suhrob.movieinfoapp.presentation.ui.movies

import com.arkivanov.decompose.value.Value
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.components.Category

interface Movies {

    val state: Value<MoviesState>

    fun sendEvent(event: MoviesEvent)

    data class MoviesState(
        val selectedCategory: Category = Category.values().first(),
        val categories: List<Category> = Category.values().toList(),
        val selectedGenre: Genre = defaultGenre,
        val genres: List<Genre> = listOf(),
        val movies: List<Movie> = listOf(),
        val uiState: UiState = UiState.success,
    )
}