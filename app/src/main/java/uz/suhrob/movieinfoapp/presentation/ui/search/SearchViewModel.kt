package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {
    val query = mutableStateOf("")
    val movies = mutableStateOf<Resource<List<Movie>>>(Resource.Success(listOf()))

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun search() = viewModelScope.launch {
        val result = repository.searchMovies(query.value, DEFAULT_PAGE)
        movies.value = result
    }
}