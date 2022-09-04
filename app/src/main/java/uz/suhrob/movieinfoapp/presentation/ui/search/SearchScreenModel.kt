package uz.suhrob.movieinfoapp.presentation.ui.search

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource

class SearchScreenModel constructor(
    private val repository: MovieRepository
): ScreenModel {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _movies: MutableStateFlow<Resource<List<Movie>>> =
        MutableStateFlow(Resource.Success(listOf()))
    val movies: StateFlow<Resource<List<Movie>>> get() = _movies

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onTriggerEvent(event: SearchEvent) {
        coroutineScope.launch {
            when (event) {
                is SearchEvent -> search()
                else -> Unit
            }
        }
    }

    private suspend fun search() {
        _movies.value = Resource.Loading()
        val result = repository.searchMovies(query.value, DEFAULT_PAGE)
        _movies.value = result
    }
}