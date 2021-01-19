package uz.suhrob.movieinfoapp.presentation.ui.details

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.INITIAL
import uz.suhrob.movieinfoapp.presentation.components.animations.LikeState.LIKED
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {
    var movieId: Int = 0

    val movie = mutableStateOf<Resource<Movie>>(Resource.Loading())
    val videos = mutableStateOf<List<Video>>(listOf())
    val reviews = mutableStateOf<List<Review>>(listOf())

    val likeState = mutableStateOf(INITIAL)

    init {
        favoritesRepository.isMovieFavorite(movieId).onEach {
            likeState.value = if (it) LIKED else INITIAL
        }.launchIn(viewModelScope)
        loadMovie()
        loadReviews()
    }

    fun loadMovie() = viewModelScope.launch {
        val result = repository.getMovie(movieId)
        if (result is Resource.Error && likeState.value == LIKED) {
            getMovie()
        } else {
            movie.value = result
        }
    }

    fun loadVideos() = viewModelScope.launch {
        Log.d("AppDebug", "loadVideos start")
        val result = repository.getMovieVideos(movieId)
        result.data?.let { videos.value = it; Log.d("AppDebug", "$it") }
        result.message?.let { Log.d("AppDebug", "DetailsViewModel: $it") }
        Log.d("AppDebug", "loadVideos end")
    }

    fun loadReviews() = viewModelScope.launch {
        Log.d("AppDebug", "loadReviews start")
        val result = repository.getMovieReviews(movieId, DEFAULT_PAGE)
        result.data?.let { reviews.value = it }
        Log.d("AppDebug", "loadReviews end")
    }

    fun getMovie() = viewModelScope.launch {
        movie.value = Resource.Success(favoritesRepository.getMovie(movieId))
    }

    fun likeClick() = viewModelScope.launch {
        if (likeState.value == INITIAL) {
            favoritesRepository.insertMovie(movie.value.data!!)
        } else {
            favoritesRepository.deleteMovie(movie.value.data!!)
        }
    }
}