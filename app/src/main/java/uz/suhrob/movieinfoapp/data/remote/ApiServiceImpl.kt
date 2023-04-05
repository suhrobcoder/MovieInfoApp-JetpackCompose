package uz.suhrob.movieinfoapp.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.data.remote.response.*
import uz.suhrob.movieinfoapp.other.API_KEY
import uz.suhrob.movieinfoapp.other.BASE_URL

class ApiServiceImpl(
    private val client: HttpClient,
) : ApiService {
    override suspend fun getGenres(): GenreResponse {
        return client.get {
            url("/3/genre/movie/list")
        }.body()
    }

    override suspend fun getMovie(movieId: Int): MovieDto {
        return client.get {
            url("/3/movie/$movieId")
        }.body()
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewResponse {
        return client.get {
            url("/3/movie/movieId/reviews")
            parameter("page", page)
        }.body()
    }

    override suspend fun getMovieVideos(movieId: Int): MovieVideoResponse {
        return client.get {
            url("/3/movie/$movieId/videos")
        }.body()
    }

    override suspend fun getPopularMovies(page: Int): MovieListResponse {
        return client.get {
            url("/3/movie/popular")
            parameter("page", page)
        }.body()
    }

    override suspend fun getTopRatedMovies(page: Int): MovieListResponse {
        return client.get {
            url("/3/movie/top_rated")
            parameter("page", page)
        }.body()
    }

    override suspend fun getUpcomingMovies(page: Int): MovieListResponse {
        return client.get {
            url("/3/movie/upcoming")
            parameter("page", page)
        }.body()
    }

    override suspend fun searchMovies(query: String, page: Int): MovieListResponse {
        return client.get {
            url("/3/search/movie")
            parameter("query", query)
            parameter("page", page)
        }.body()
    }

    override suspend fun getMovieCredits(movieId: Int): CreditsResponse {
        return client.get {
            url("/3/movie/$movieId/credits")
        }.body()
    }

    override suspend fun createGuestSession(): GuestSessionResponse {
        return client.get {
            url("/3/authentication/guest_session/new")
        }.body()
    }

    override suspend fun rateMovie(
        movieId: Int,
        guestSessionId: String,
        rate: RateBody
    ): RateResponse {
        return client.post {
            url("/3/movie/$movieId/rating")
            parameter("guest_session_id", guestSessionId)
            setBody(rate)
            headers {
                contentType(ContentType.Application.Json)
            }
        }.body()
    }
}

fun ApiService(): ApiService {
    return ApiServiceImpl(
        HttpClient(Android) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    parameters.append("api_key", API_KEY)
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    )
}