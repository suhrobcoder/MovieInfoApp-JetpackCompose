package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.base.BaseViewModel

class SearchViewModel(
    private val repository: MovieRepository,
) : BaseViewModel<SearchState, SearchEvent, SearchEffect>(SearchState()) {

    override fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChange -> setState { copy(query = event.query) }
            is SearchEvent.MovieClick -> sendEffect(SearchEffect.NavigateToDetails(event.movie))
            SearchEvent.ExecuteSearch -> search()
        }
    }

    private fun search() {
        viewModelScope.launch {
            setState { copy(uiState = UiState.loading) }
            when (val result = repository.searchMovies(currentState.query, DEFAULT_PAGE)) {
                is Resource.Error -> setState { copy(uiState = UiState.error) }
                is Resource.Loading -> Unit
                is Resource.Success -> setState {
                    copy(
                        uiState = UiState.success,
                        movies = result.data ?: listOf(),
                    )
                }
            }
        }
    }
}
