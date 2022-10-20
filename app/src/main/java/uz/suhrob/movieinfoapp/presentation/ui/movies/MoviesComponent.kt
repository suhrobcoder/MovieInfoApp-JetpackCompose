package uz.suhrob.movieinfoapp.presentation.ui.movies

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.components.Category

class MoviesComponent(
    private val repository: MovieRepository,
    private val ioScope: CoroutineScope,
    private val navigateToDetails: (Movie) -> Unit,
) : Movies {

    private val _state = MutableValue(Movies.MoviesState())
    override val state: Value<Movies.MoviesState> = _state

    private var currentPage = DEFAULT_PAGE
    private var loadingMoviesJob: Job? = null

    init {
        sendEvent(MoviesEvent.LoadGenres)
    }

    override fun sendEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.ChangeCategory -> changeCategory(event.category)
            MoviesEvent.LoadGenres -> loadGenres()
            MoviesEvent.LoadMovies -> loadMovies()
            MoviesEvent.NextPage -> nextPage()
            is MoviesEvent.SelectGenre -> setSelectedGenre(event.genre)
            is MoviesEvent.MovieClicked -> navigateToDetails(event.movie)
        }
    }

    private fun changeCategory(category: Category) {
        if (state.value.selectedCategory != category) {
            _state.reduce { it.copy(selectedCategory = category, movies = listOf()) }
            loadMovies()
        }
    }

    private fun setSelectedGenre(genre: Genre) {
        if (state.value.selectedGenre != genre) {
            _state.reduce { it.copy(selectedGenre = genre) }
        }
    }

    private fun loadGenres() {
        ioScope.launch {
            when (val result = repository.getGenres()) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> _state.reduce {
                    it.copy(genres = result.data.orEmpty() + listOf(defaultGenre))
                }
            }
        }
    }

    private fun nextPage() {
        currentPage++
        loadMovies()
    }

    private fun loadMovies() {
        loadingMoviesJob?.cancel()
        loadingMoviesJob = ioScope.launch {
            _state.reduce { it.copy(uiState = UiState.loading) }
            val result = when (state.value.selectedCategory) {
                Category.POPULAR -> repository.getPopularMovies(currentPage)
                Category.TOP_RATED -> repository.getTopRatedMovies(currentPage)
                Category.UPCOMING -> repository.getUpcomingMovies(currentPage)
            }
            when (result) {
                is Resource.Error -> _state.reduce { it.copy(uiState = UiState.error) }
                is Resource.Loading -> Unit
                is Resource.Success -> _state.reduce {
                    it.copy(
                        uiState = UiState.error,
                        movies = state.value.movies + result.data.orEmpty(),
                    )
                }
            }
        }
    }
}