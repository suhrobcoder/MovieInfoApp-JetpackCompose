package uz.suhrob.movieinfoapp.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.data.remote.response.CreditsResponse
import uz.suhrob.movieinfoapp.data.remote.response.GenreResponse
import uz.suhrob.movieinfoapp.data.remote.response.GuestSessionResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieListResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieReviewResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieVideoResponse
import uz.suhrob.movieinfoapp.data.remote.response.RateBody
import uz.suhrob.movieinfoapp.data.remote.response.RateResponse
import uz.suhrob.movieinfoapp.other.BASE_URL

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int
    ): Response<MovieDto>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): Response<MovieReviewResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int
    ): Response<MovieVideoResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Response<CreditsResponse>

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(): Response<GuestSessionResponse>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") guestSessionId: String,
        @Body rate: RateBody
    ): Response<RateResponse>
}

@OptIn(ExperimentalSerializationApi::class)
fun ApiService(): ApiService {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .client(OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build())
        .build()
        .create(ApiService::class.java)
}

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}