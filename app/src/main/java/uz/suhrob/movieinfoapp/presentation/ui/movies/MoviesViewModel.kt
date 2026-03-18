package uz.suhrob.movieinfoapp.presentation.ui.movies

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.base.BaseViewModel
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.presentation.components.Category
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val repository: MovieRepository,
) : BaseViewModel<MoviesState, MoviesEvent, MoviesEffect>(MoviesState()) {

    private var currentPage = DEFAULT_PAGE
    private var loadingMoviesJob: Job? = null

    init {
        loadGenres()
        loadMovies()
    }

    override fun handleEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.ChangeCategory -> changeCategory(event.category)
            MoviesEvent.LoadGenres -> loadGenres()
            MoviesEvent.LoadMovies -> loadMovies()
            MoviesEvent.NextPage -> nextPage()
            is MoviesEvent.SelectGenre -> setSelectedGenre(event.genre)
            is MoviesEvent.MovieClicked -> sendEffect(MoviesEffect.NavigateToDetails(event.movie))
        }
    }

    private fun changeCategory(category: Category) {
        if (currentState.selectedCategory != category) {
            currentPage = DEFAULT_PAGE
            setState { copy(selectedCategory = category, movies = listOf()) }
            loadMovies()
        }
    }

    private fun setSelectedGenre(genre: Genre) {
        if (currentState.selectedGenre != genre) {
            setState { copy(selectedGenre = genre) }
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            when (val result = repository.getGenres()) {
                is Resource.Error -> Unit
                is Resource.Loading -> Unit
                is Resource.Success -> setState {
                    copy(genres = result.data.orEmpty() + listOf(defaultGenre))
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
        loadingMoviesJob = viewModelScope.launch {
            setState { copy(uiState = UiState.loading) }
            val result = when (currentState.selectedCategory) {
                Category.POPULAR -> repository.getPopularMovies(currentPage)
                Category.TOP_RATED -> repository.getTopRatedMovies(currentPage)
                Category.UPCOMING -> repository.getUpcomingMovies(currentPage)
            }
            when (result) {
                is Resource.Error -> setState { copy(uiState = UiState.error) }
                is Resource.Loading -> Unit
                is Resource.Success -> setState {
                    copy(
                        uiState = UiState.success,
                        movies = movies + result.data.orEmpty(),
                    )
                }
            }
        }
    }
}
