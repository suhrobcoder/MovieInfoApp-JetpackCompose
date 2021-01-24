package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.INITIAL
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.LIKED

class DetailsViewModel(
    private val repository: MovieRepository,
    private val favoritesRepository: FavoritesRepository,
    private val movieId: Int
) : ViewModel() {
    private val _movie = MutableStateFlow<Resource<Movie>>(Resource.Loading())
    val movie: StateFlow<Resource<Movie>> get() = _movie

    private val _videos = MutableStateFlow<List<Video>>(listOf())
    val videos: StateFlow<List<Video>> get() = _videos

    private val _cast = MutableStateFlow(listOf<Cast>())
    val cast: StateFlow<List<Cast>> get() = _cast

    private val _reviews = MutableStateFlow<List<Review>>(listOf())
    val reviews: StateFlow<List<Review>> get() = _reviews

    private val _likeState = MutableStateFlow(INITIAL)
    val likeState: StateFlow<LikeState> get() = _likeState

    init {
        favoritesRepository.isMovieFavorite(movieId).onEach {
            _likeState.value = if (it) LIKED else INITIAL
        }.launchIn(viewModelScope)
        onTriggerEvent(DetailsEvent.LoadMovie)
        onTriggerEvent(DetailsEvent.LoadReviews)
    }

    fun onTriggerEvent(event: DetailsEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailsEvent.LoadMovie -> loadMovie()
                is DetailsEvent.LoadVideos -> loadVideos()
                is DetailsEvent.LoadCast -> loadCast()
                is DetailsEvent.LoadReviews -> loadReviews()
                is DetailsEvent.LikeClick -> likeClick()
            }
        }
    }

    private suspend fun loadMovie() {
        val result = repository.getMovie(movieId)
        if (result is Resource.Error && likeState.value == LIKED) {
            getMovie()
        } else {
            _movie.value = result
        }
    }

    private suspend fun loadVideos() {
        val result = repository.getMovieVideos(movieId)
        result.data?.let { _videos.value = it }
    }

    private suspend fun loadCast() {
        val result = repository.getMovieCredits(movieId)
        result.data?.let { _cast.value = it }
    }

    private suspend fun loadReviews() {
        val result = repository.getMovieReviews(movieId, DEFAULT_PAGE)
        result.data?.let { _reviews.value = it }
    }

    fun getMovie() = viewModelScope.launch {
        _movie.emit(Resource.Success(favoritesRepository.getMovie(movieId)))
    }

    private suspend fun likeClick() {
        if (likeState.value == INITIAL) {
            favoritesRepository.insertMovie(_movie.value.data!!)
        } else {
            favoritesRepository.deleteMovie(_movie.value.data!!)
        }
    }
}