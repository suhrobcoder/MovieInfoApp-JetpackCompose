package uz.suhrob.movieinfoapp.presentation.ui.search

import com.arkivanov.decompose.value.Value
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.UiState

interface Search {
    val state: Value<SearchState>

    fun sendEvent(event: SearchEvent)

    data class SearchState(
        val query: String = "",
        val movies: List<Movie> = listOf(),
        val uiState: UiState = UiState.success,
    )
}