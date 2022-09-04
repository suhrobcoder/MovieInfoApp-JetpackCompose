package uz.suhrob.movieinfoapp.presentation.ui.favorites

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.model.Movie

class FavoritesScreenModel constructor(
    private val repository: FavoritesRepository
): ScreenModel {
    private val _movies = MutableStateFlow<List<Movie>>(listOf())
    val movies: StateFlow<List<Movie>> get() = _movies

    init {
        coroutineScope.launch {
            repository.getAllMovies().collect {
                _movies.value = it
            }
        }
    }
}
