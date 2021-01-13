package uz.suhrob.movieinfoapp.presentation.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.DEFAULT_PAGE

class DetailsViewModel(private val repository: MovieRepository, movie: Movie): ViewModel() {
    val movie = mutableStateOf(movie)
    val videos = mutableStateOf<List<Video>>(listOf())
    val reviews = mutableStateOf<List<Review>>(listOf())

    init {
        loadMovie(movie.id)
        if (movie.video) {
            loadVideos(movie.id)
        }
        loadReviews(movie.id)
    }

    private fun loadMovie(movieId: Int) = viewModelScope.launch {
        val result = repository.getMovie(movieId)
        result.data?.let { movie.value = it }
    }

    private fun loadVideos(movieId: Int) = viewModelScope.launch {
        val result = repository.getMovieVideos(movieId)
        result.data?.let { videos.value = it }
    }

    private fun loadReviews(movieId: Int) = viewModelScope.launch {
        val result = repository.getMovieReviews(movieId, DEFAULT_PAGE)
        result.data?.let { reviews.value = it }
    }
}