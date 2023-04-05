package uz.suhrob.movieinfoapp.data.remote

import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.data.remote.response.*

interface ApiService {
    suspend fun getGenres(): GenreResponse
    suspend fun getMovie(movieId: Int): MovieDto
    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewResponse
    suspend fun getMovieVideos(movieId: Int): MovieVideoResponse
    suspend fun getPopularMovies(page: Int): MovieListResponse
    suspend fun getTopRatedMovies(page: Int): MovieListResponse
    suspend fun getUpcomingMovies(page: Int): MovieListResponse
    suspend fun searchMovies(query: String,page: Int): MovieListResponse
    suspend fun getMovieCredits(movieId: Int): CreditsResponse
    suspend fun createGuestSession(): GuestSessionResponse
    suspend fun rateMovie(movieId: Int,guestSessionId: String, rate: RateBody): RateResponse
}