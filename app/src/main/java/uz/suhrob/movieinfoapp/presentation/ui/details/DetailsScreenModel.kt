package uz.suhrob.movieinfoapp.presentation.ui.details

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.MAX_RATING
import uz.suhrob.movieinfoapp.presentation.components.LikeState
import uz.suhrob.movieinfoapp.presentation.components.LikeState.INITIAL
import uz.suhrob.movieinfoapp.presentation.components.LikeState.LIKED

class DetailsScreenModel constructor(
    private val repository: MovieRepository,
    private val favoritesRepository: FavoritesRepository
) : ScreenModel {
    var movieId: Int = 0
        set(value) {
            field = value
            load()
        }

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

    private val _rating = MutableStateFlow(MAX_RATING)
    val rating: StateFlow<Int> get() = _rating

    private val _isShowingDialog = MutableStateFlow(false)
    val isShowingDialog: StateFlow<Boolean> get() = _isShowingDialog

    private val snackBarChannel = Channel<String>()
    val snackBarFlow = snackBarChannel.receiveAsFlow()

    private fun load() {
        favoritesRepository.isMovieFavorite(movieId).onEach {
            _likeState.value = if (it) LIKED else INITIAL
        }.launchIn(coroutineScope)
        onTriggerEvent(DetailsEvent.LoadMovie)
        onTriggerEvent(DetailsEvent.LoadReviews)
        onTriggerEvent(DetailsEvent.LoadCast)
    }

    fun onTriggerEvent(event: DetailsEvent) {
        coroutineScope.launch {
            when (event) {
                is DetailsEvent.LoadMovie -> loadMovie()
                is DetailsEvent.LoadVideos -> loadVideos()
                is DetailsEvent.LoadCast -> loadCast()
                is DetailsEvent.LoadReviews -> loadReviews()
                is DetailsEvent.LikeClick -> likeClick()
                is DetailsEvent.SubmitRating -> submitRating(event.rating)
                is DetailsEvent.ShowDialog -> showDialog()
                is DetailsEvent.CloseDialog -> closeDialog()
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

    private fun getMovie() = coroutineScope.launch {
        _movie.emit(Resource.Success(favoritesRepository.getMovie(movieId)))
    }

    private suspend fun likeClick() {
        if (likeState.value == INITIAL) {
            favoritesRepository.insertMovie(_movie.value.data!!)
        } else {
            favoritesRepository.deleteMovie(_movie.value.data!!)
        }
    }

    private suspend fun submitRating(rating: Int) {
        closeDialog()
        val result = repository.rateMovie(movieId, rating)
        if (result is Resource.Error || (result is Resource.Success && result.data != true)) {
            snackBarChannel.send("Movie rating error")
        } else {
            snackBarChannel.send("Movie rated successfully")
        }
    }

    fun setRating(rating: Int) {
        _rating.value = rating
    }

    private fun showDialog() {
        _isShowingDialog.value = true
    }

    private fun closeDialog() {
        _isShowingDialog.value = false
        _rating.value = MAX_RATING
    }
}