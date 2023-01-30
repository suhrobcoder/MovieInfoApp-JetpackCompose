package uz.suhrob.movieinfoapp.presentation.ui.search

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.UiState

class SearchComponent(
    private val repository: MovieRepository,
    private val ioScope: CoroutineScope,
    private val navigateToDetails: (Movie) -> Unit,
) : Search {

    private val _state = MutableValue(Search.SearchState())
    override val state: Value<Search.SearchState> = _state

    override fun sendEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.MovieClick -> movieClicked(event.movie)
            is SearchEvent.QueryChange -> queryChanged(event.query)
            SearchEvent.ExecuteSearch -> search()
        }
    }

    private fun queryChanged(query: String) {
        _state.reduce { it.copy(query = query) }
    }

    private fun movieClicked(movie: Movie) {
        navigateToDetails(movie)
    }

    private fun search() {
        ioScope.launch {
            _state.reduce { it.copy(uiState = UiState.loading) }
            when (val result = repository.searchMovies(_state.value.query, DEFAULT_PAGE)) {
                is Resource.Error -> _state.reduce { it.copy(uiState = UiState.error) }
                is Resource.Loading -> Unit
                is Resource.Success -> _state.reduce {
                    it.copy(
                        uiState = UiState.success,
                        movies = result.data ?: listOf()
                    )
                }
            }
        }
    }
}