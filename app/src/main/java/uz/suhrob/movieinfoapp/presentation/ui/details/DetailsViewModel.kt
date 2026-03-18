package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.base.BaseViewModel
import uz.suhrob.movieinfoapp.presentation.components.MAX_RATING

class DetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository,
) : BaseViewModel<DetailsState, DetailsEvent, DetailsEffect>(DetailsState()) {

    init {
        favoritesRepository.isMovieFavorite(movieId).onEach { liked ->
            setState { copy(liked = liked) }
        }.launchIn(viewModelScope)
        loadMovie()
        loadReviews()
        loadVideos()
        loadCast()
    }

    override fun handleEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.CloseDialog -> setState { copy(showDialog = false, dialogRating = MAX_RATING) }
            DetailsEvent.ShowDialog -> setState { copy(showDialog = true) }
            is DetailsEvent.SetRating -> setState { copy(dialogRating = event.rating) }
            DetailsEvent.LikeClick -> likeClick()
            DetailsEvent.LoadCast -> loadCast()
            DetailsEvent.LoadMovie -> loadMovie()
            DetailsEvent.LoadReviews -> loadReviews()
            DetailsEvent.LoadVideos -> loadVideos()
            DetailsEvent.SubmitRating -> submitRating()
            DetailsEvent.NavigateBack -> sendEffect(DetailsEffect.NavigateBack)
        }
    }

    private fun loadMovie() {
        viewModelScope.launch {
            val result = movieRepository.getMovie(movieId)
            result.data?.let { movie -> setState { copy(movie = movie) } }
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            val result = movieRepository.getMovieVideos(movieId)
            result.data?.let { videos -> setState { copy(videos = videos) } }
        }
    }

    private fun loadReviews() {
        viewModelScope.launch {
            val result = movieRepository.getMovieReviews(movieId, DEFAULT_PAGE)
            result.data?.let { reviews -> setState { copy(reviews = reviews) } }
        }
    }

    private fun loadCast() {
        viewModelScope.launch {
            val result = movieRepository.getMovieCredits(movieId)
            result.data?.let { cast -> setState { copy(cast = cast) } }
        }
    }

    private fun likeClick() {
        val movie = currentState.movie ?: return
        viewModelScope.launch {
            if (!currentState.liked) {
                favoritesRepository.insertMovie(movie)
            } else {
                favoritesRepository.deleteMovie(movie)
            }
        }
    }

    private fun submitRating() {
        setState { copy(showDialog = false, dialogRating = MAX_RATING) }
        viewModelScope.launch {
            val result = movieRepository.rateMovie(movieId, currentState.dialogRating)
            val message = if (result is Resource.Error || (result is Resource.Success && result.data != true)) {
                "Movie rating error"
            } else {
                "Movie rated successfully"
            }
            sendEffect(DetailsEffect.ShowSnackbar(message))
        }
    }
}
