package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Category

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    val category = mutableStateOf(Category.POPULAR)
    val genres = mutableStateOf<List<Genre>>(listOf())
    val selectedGenre = mutableStateOf(defaultGenre)
    val movies = mutableStateOf<Resource<List<Movie>>>(Resource.Success(listOf()))

    init {
        loadGenres()
        loadMovies()
    }

    fun changeCategory(newCategory: Category) {
        category.value = newCategory
        loadMovies()
    }

    private fun loadGenres() = viewModelScope.launch {
        val result = repository.getGenres()
        genres.value = result.data?.let {
            listOf(defaultGenre) + it
        } ?: listOf()
    }

    fun loadMovies() = viewModelScope.launch {
        val result = when (category.value) {
            Category.POPULAR -> repository.getPopularMovies(DEFAULT_PAGE)
            Category.TOP_RATED -> repository.getTopRatedMovies(DEFAULT_PAGE)
            Category.UPCOMING -> repository.getUpcomingMovies(DEFAULT_PAGE)
        }
        movies.value = result
    }
}