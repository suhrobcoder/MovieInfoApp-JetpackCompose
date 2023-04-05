package uz.suhrob.movieinfoapp.data.repository

import kotlinx.coroutines.flow.first
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.mapper.*
import uz.suhrob.movieinfoapp.data.remote.response.RateBody
import uz.suhrob.movieinfoapp.domain.model.*
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.other.MAX_CAST_COUNT
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.safeCall

class MovieRepositoryImpl(
    private val service: ApiService,
    private val prefs: MovieInfoPref
) : MovieRepository {
    override suspend fun getGenres(): Resource<List<Genre>> {
        return safeCall {
            Resource.Success(
                GenreMapper.mapToList(
                    service.getGenres().genres
                )
            )
        }
    }

    override suspend fun getMovie(movieId: Int): Resource<Movie> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToDomainModel(
                    service.getMovie(movieId)
                )
            )
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>> {
        return safeCall {
            Resource.Success(
                ReviewMapper.mapToList(
                    service.getMovieReviews(movieId, page).results
                )
            )
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Resource<List<Video>> {
        return safeCall {
            Resource.Success(
                VideoMapper.mapToList(
                    service.getMovieVideos(movieId).results
                )
            )
        }
    }

    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getPopularMovies(page).results
                )
            )
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getTopRatedMovies(page).results
                )
            )
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getUpcomingMovies(page).results
                )
            )
        }
    }

    override suspend fun searchMovies(query: String, page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.searchMovies(query, page).results
                )
            )
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Resource<List<Cast>> {
        return safeCall {
            Resource.Success(
                CastDtoMapper.mapToList(
                    service.getMovieCredits(movieId).cast
                        .filter { it.order < MAX_CAST_COUNT }
                )
            )
        }
    }

    override suspend fun rateMovie(movieId: Int, value: Int): Resource<Boolean> {
        val sessionId = try {
            prefs.guestSessionId.first() ?: throw IllegalStateException("Session id is null")
        } catch (e: Exception) {
            createSessionId()?.also { id ->
                prefs.setGuestSessionId(id)
            }
        }
        return sessionId?.let { id ->
            safeCall {
                Resource.Success(
                    service.rateMovie(movieId, id, RateBody(value)).statusMessage
                        .contains("Success"),
                )
            }
        } ?: Resource.Error(message = "Error")
    }

    private suspend fun createSessionId(): String? {
        return try {
            val result = service.createGuestSession()
            result.guestSessionId
        } catch (e: Exception) {
            null
        }
    }
}