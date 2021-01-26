package uz.suhrob.movieinfoapp.data.repository

import uz.suhrob.movieinfoapp.domain.model.*
import uz.suhrob.movieinfoapp.other.Resource

interface MovieRepository {
    suspend fun getGenres(): Resource<List<Genre>>
    suspend fun getMovie(movieId: Int): Resource<Movie>
    suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>>
    suspend fun getMovieVideos(movieId: Int): Resource<List<Video>>
    suspend fun getPopularMovies(page: Int): Resource<List<Movie>>
    suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>>
    suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>>
    suspend fun searchMovies(query: String, page: Int): Resource<List<Movie>>
    suspend fun getMovieCredits(movieId: Int): Resource<List<Cast>>
    suspend fun rateMovie(movieId: Int, value: Int): Resource<Boolean>
}