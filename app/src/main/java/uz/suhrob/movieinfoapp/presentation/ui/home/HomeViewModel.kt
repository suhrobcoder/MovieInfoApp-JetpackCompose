package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.PAGE_SIZE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Category

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    val category = mutableStateOf(Category.POPULAR)
    val genres = mutableStateOf<List<Genre>>(listOf())
    val selectedGenre = mutableStateOf(defaultGenre)
    val movies = mutableStateOf<List<Movie>>(listOf())
    val loading = mutableStateOf(false)
    val error = mutableStateOf(false)

    private var loadMoviesJob: Job? = null

    val currentPage = mutableStateOf(DEFAULT_PAGE)
    var movieListScrollPosition = 0

    init {
        loadGenres()
        loadMovies()
    }

    fun changeCategory(newCategory: Category) {
        if (category.value != newCategory) {
            category.value = newCategory
            currentPage.value = DEFAULT_PAGE
            movies.value = listOf()
            loadMovies()
        }
    }

    private fun loadGenres() = viewModelScope.launch {
        val result = repository.getGenres()
        genres.value = result.data?.let {
            listOf(defaultGenre) + it
        } ?: listOf()
    }

    fun nextPage() {
        viewModelScope.launch {
            if (movieListScrollPosition + 1 >= currentPage.value * PAGE_SIZE) {
                incrementPage()
                loadMovies()
            }
        }
    }

    private fun appendMovies(newMovies: List<Movie>) {
        val current = ArrayList(movies.value)
        current.addAll(newMovies)
        movies.value = current
    }

    private fun incrementPage() {
        currentPage.value += 1
    }

    fun onChangeScrollPosition(position: Int) {
        movieListScrollPosition = position
    }

    fun loadMovies() = viewModelScope.launch {
        loadMoviesJob?.cancel()
        loadMoviesJob = CoroutineScope(viewModelScope.coroutineContext).launch {
            loading.value = true
            error.value = false
            val result = when (category.value) {
                Category.POPULAR -> repository.getPopularMovies(currentPage.value)
                Category.TOP_RATED -> repository.getTopRatedMovies(currentPage.value)
                Category.UPCOMING -> repository.getUpcomingMovies(currentPage.value)
            }
            loading.value = false
            error.value = result is Resource.Error
            appendMovies(result.data ?: listOf())
        }
    }
}