package uz.suhrob.movieinfoapp.presentation.ui.movies

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Category

class MoviesScreenModel constructor(
    private val repository: MovieRepository
) : ScreenModel {
    private val _category = MutableStateFlow(Category.POPULAR)
    val category: StateFlow<Category> get() = _category

    private val _genres = MutableStateFlow(listOf<Genre>())
    val genres: StateFlow<List<Genre>> get() = _genres

    private val _selectedGenre = MutableStateFlow(defaultGenre)
    val selectedGenre: StateFlow<Genre> get() = _selectedGenre

    private val _movies = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> get() = _movies

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> get() = _error

    private var loadMoviesJob: Job? = null

    private val _currentPage = MutableStateFlow(DEFAULT_PAGE)
    private val currentPage: StateFlow<Int> get() = _currentPage

    init {
        onTriggerEvent(MoviesEvent.LoadGenres)
        onTriggerEvent(MoviesEvent.LoadMovies)
    }

    fun onTriggerEvent(event: MoviesEvent) {
        coroutineScope.launch {
            when (event) {
                is MoviesEvent.ChangeCategory -> changeCategory(event.category)
                is MoviesEvent.LoadGenres -> loadGenres()
                is MoviesEvent.LoadMovies -> loadMovies()
                is MoviesEvent.NextPage -> nextPage()
            }
        }
    }

    private suspend fun changeCategory(newCategory: Category) {
        if (category.value != newCategory) {
            _category.value = newCategory
            _currentPage.value = DEFAULT_PAGE
            _movies.value = listOf()
            loadMovies()
        }
    }

    fun setSelectedGenre(genre: Genre) {
        _selectedGenre.value = genre
    }

    private suspend fun loadGenres() {
        val result = repository.getGenres()
        _genres.value = result.data?.let {
            listOf(defaultGenre) + it
        } ?: listOf()
    }

    private fun nextPage() {
        coroutineScope.launch {
            if (!loading.value) {
                incrementPage()
                loadMovies()
            }
        }
    }

    private fun appendMovies(newMovies: List<Movie>) {
        val current = ArrayList(_movies.value)
        current.addAll(newMovies)
        _movies.value = current
    }

    private fun incrementPage() {
        _currentPage.value += 1
    }

    private suspend fun loadMovies() {
        loadMoviesJob?.cancel()
        loadMoviesJob = CoroutineScope(coroutineScope.coroutineContext).launch {
            _loading.value = true
            _error.value = false
            val result = when (category.value) {
                Category.POPULAR -> repository.getPopularMovies(currentPage.value)
                Category.TOP_RATED -> repository.getTopRatedMovies(currentPage.value)
                Category.UPCOMING -> repository.getUpcomingMovies(currentPage.value)
            }
            _loading.value = false
            _error.value = result is Resource.Error
            appendMovies(result.data ?: listOf())
        }
    }
}