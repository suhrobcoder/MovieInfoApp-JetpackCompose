package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import uz.suhrob.movieinfoapp.other.PAGE_SIZE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Category
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {
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
    val currentPage: StateFlow<Int> get() = _currentPage

    var movieListScrollPosition = 0

    init {
        onTriggerEvent(HomeEvent.LoadGenres)
        onTriggerEvent(HomeEvent.LoadMovies)
    }

    fun onTriggerEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.ChangeCategory -> changeCategory(event.category)
                is HomeEvent.LoadGenres -> loadGenres()
                is HomeEvent.LoadMovies -> loadMovies()
                is HomeEvent.NextPage -> nextPage()
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
        viewModelScope.launch {
            if (movieListScrollPosition + 1 >= currentPage.value * PAGE_SIZE) {
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

    fun onChangeScrollPosition(position: Int) {
        movieListScrollPosition = position
    }

    private suspend fun loadMovies() {
        loadMoviesJob?.cancel()
        loadMoviesJob = CoroutineScope(viewModelScope.coroutineContext).launch {
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