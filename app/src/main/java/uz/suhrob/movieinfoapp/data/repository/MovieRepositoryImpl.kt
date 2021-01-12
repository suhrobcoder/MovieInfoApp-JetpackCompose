package uz.suhrob.movieinfoapp.data.repository

import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.mapper.GenreMapper
import uz.suhrob.movieinfoapp.data.remote.mapper.MovieMapper
import uz.suhrob.movieinfoapp.data.remote.mapper.ReviewMapper
import uz.suhrob.movieinfoapp.data.remote.mapper.VideoMapper
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.other.safeCall

class MovieRepositoryImpl(
    private val service: ApiService
) : MovieRepository {
    override suspend fun getGenres(): Resource<List<Genre>> {
        return safeCall {
            Resource.Success(
                GenreMapper.mapToList(
                    service.getGenres().body()!!.genres
                )
            )
        }
    }

    override suspend fun getMovie(movieId: Int): Resource<Movie> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToDomainModel(
                    service.getMovie(movieId).body()!!
                )
            )
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): Resource<List<Review>> {
        return safeCall {
            Resource.Success(
                ReviewMapper.mapToList(
                    service.getMovieReviews(movieId, page).body()!!.results
                )
            )
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Resource<List<Video>> {
        return safeCall {
            Resource.Success(
                VideoMapper.mapToList(
                    service.getMovieVideos(movieId).body()!!.results
                )
            )
        }
    }

    override suspend fun getPopularMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getPopularMovies(page).body()!!.results
                )
            )
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getPopularMovies(page).body()!!.results
                )
            )
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Resource<List<Movie>> {
        return safeCall {
            Resource.Success(
                MovieMapper.mapToList(
                    service.getPopularMovies(page).body()!!.results
                )
            )
        }
    }
}