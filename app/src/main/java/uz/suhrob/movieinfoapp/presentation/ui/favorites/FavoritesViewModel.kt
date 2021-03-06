package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
): ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> get() = _movies

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect {
                _movies.value = it
            }
        }
    }
}

