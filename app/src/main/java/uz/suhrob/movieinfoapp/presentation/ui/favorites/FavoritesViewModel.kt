package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.model.Movie

class FavoritesViewModel(
    private val repository: FavoritesRepository
): ViewModel() {
    val movies = mutableStateOf<List<Movie>>(listOf())

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect {
                movies.value = it
            }
        }
    }
}

