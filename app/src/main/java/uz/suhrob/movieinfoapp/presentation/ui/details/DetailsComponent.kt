package uz.suhrob.movieinfoapp.presentation.ui.details

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.MAX_RATING

class DetailsComponent(
    private val movie: Movie,
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository,
    private val ioScope: CoroutineScope,
    private val navigateBack: () -> Unit,
) : Details {

    private val _state = MutableValue(Details.DetailsState(movie))
    override val state: Value<Details.DetailsState> = _state

    private val _dialogState = MutableValue(Details.DialogState())
    override val dialogState: Value<Details.DialogState> = _dialogState

    private val snackBarChannel = Channel<String>()
    override val snackBarFlow = snackBarChannel.receiveAsFlow()

    init {
        favoritesRepository.isMovieFavorite(movie.id).onEach { liked ->
            _state.reduce { it.copy(liked = liked) }
        }.launchIn(ioScope)
        sendEvent(DetailsEvent.LoadMovie)
        sendEvent(DetailsEvent.LoadReviews)
        sendEvent(DetailsEvent.LoadVideos)
        sendEvent(DetailsEvent.LoadCast)
    }

    override fun sendEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.CloseDialog -> closeDialog()
            DetailsEvent.LikeClick -> likeClick()
            DetailsEvent.LoadCast -> loadCast()
            DetailsEvent.LoadMovie -> loadMovie()
            DetailsEvent.LoadReviews -> loadReviews()
            DetailsEvent.LoadVideos -> loadVideos()
            DetailsEvent.ShowDialog -> showDialog()
            DetailsEvent.SubmitRating -> submitRating()
            is DetailsEvent.SetRating -> setRating(event.rating)
            DetailsEvent.NavigateBack -> navigateBack()
        }
    }

    private fun loadMovie() {
        ioScope.launch {
            val result = movieRepository.getMovie(movie.id)
            result.data?.let { movie -> _state.reduce { it.copy(movie = movie) } }
        }
    }

    private fun loadVideos() {
        ioScope.launch {
            val result = movieRepository.getMovieVideos(movie.id)
            result.data?.let { videos -> _state.reduce { it.copy(videos = videos) } }
        }
    }

    private fun loadReviews() {
        ioScope.launch {
            val result = movieRepository.getMovieReviews(movie.id, DEFAULT_PAGE)
            result.data?.let { reviews -> _state.reduce { it.copy(reviews = reviews) } }
        }
    }

    private fun loadCast() {
        ioScope.launch {
            val result = movieRepository.getMovieCredits(movie.id)
            result.data?.let { cast -> _state.reduce { it.copy(cast = cast) } }
        }
    }

    private fun likeClick() {
        ioScope.launch {
            if (!state.value.liked) {
                favoritesRepository.insertMovie(movie)
            } else {
                favoritesRepository.deleteMovie(movie)
            }
        }
    }

    private fun showDialog() {
        _dialogState.reduce { it.copy(show = true) }
    }

    private fun closeDialog() {
        _dialogState.reduce { it.copy(show = false, rating = MAX_RATING) }
    }

    private fun setRating(rating: Int) {
        _dialogState.reduce { it.copy(rating = rating) }
    }

    private fun submitRating() {
        closeDialog()
        ioScope.launch {
            val result = movieRepository.rateMovie(movie.id, _dialogState.value.rating)
            if (result is Resource.Error || (result is Resource.Success && result.data != true)) {
                snackBarChannel.send("Movie rating error")
            } else {
                snackBarChannel.send("Movie rated successfully")
            }
        }
    }
}