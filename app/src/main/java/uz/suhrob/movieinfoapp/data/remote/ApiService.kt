package uz.suhrob.movieinfoapp.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.data.remote.response.CreditsResponse
import uz.suhrob.movieinfoapp.data.remote.response.GenreResponse
import uz.suhrob.movieinfoapp.data.remote.response.GuestSessionResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieListResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieReviewResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieVideoResponse
import uz.suhrob.movieinfoapp.data.remote.response.RateBody
import uz.suhrob.movieinfoapp.data.remote.response.RateResponse

interface ApiService {

    @GET("3/genre/movie/list")
    suspend fun getGenres(): GenreResponse

    @GET("3/movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId: Int): MovieDto

    @GET("3/movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: Int,
        @Query("page") page: Int
    ): MovieReviewResponse

    @GET("3/movie/{movieId}/videos")
    suspend fun getMovieVideos(@Path("movieId") movieId: Int): MovieVideoResponse

    @GET("3/movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MovieListResponse

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MovieListResponse

    @GET("3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): MovieListResponse

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieListResponse

    @GET("3/movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): CreditsResponse

    @GET("3/authentication/guest_session/new")
    suspend fun createGuestSession(): GuestSessionResponse

    @POST("3/movie/{movieId}/rating")
    @Headers("Content-Type: application/json")
    suspend fun rateMovie(
        @Path("movieId") movieId: Int,
        @Query("guest_session_id") guestSessionId: String,
        @Body rate: RateBody
    ): RateResponse
}