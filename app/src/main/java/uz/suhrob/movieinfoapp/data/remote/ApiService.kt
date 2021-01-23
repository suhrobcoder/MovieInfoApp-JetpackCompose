package uz.suhrob.movieinfoapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.data.remote.response.GenreResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieListResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieReviewResponse
import uz.suhrob.movieinfoapp.data.remote.response.MovieVideoResponse

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
}