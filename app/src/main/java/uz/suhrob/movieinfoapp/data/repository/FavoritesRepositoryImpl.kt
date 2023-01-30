package uz.suhrob.movieinfoapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import uz.suhrob.movieinfoapp.data.local.dao.MovieDao
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieWithGenre
import uz.suhrob.movieinfoapp.data.local.mapper.MovieEntityMapper
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository

class FavoritesRepositoryImpl(
    private val movieDao: MovieDao
) : FavoritesRepository {
    override suspend fun insertMovie(movie: Movie) {
        val movieWithGenre = MovieEntityMapper.mapFromDomainModel(movie)
        val genres = movieWithGenre.genres
        val movieId = movieWithGenre.movie.id
        movieDao.insertMovie(movieWithGenre.movie)
        movieDao.insertGenres(genres)
        movieDao.insertMovieGenreCrossRef(genres.map { MovieGenreCrossRef(movieId, it.id) })
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(MovieEntityMapper.mapFromDomainModel(movie).movie)
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().transform {
            emit(
                MovieEntityMapper.mapToList(
                    it.map { movieEntity ->
                        MovieWithGenre(movieEntity, listOf())
                    }
                )
            )
        }
    }

    override suspend fun getMovie(movieId: Int): Movie {
        return MovieEntityMapper.mapToDomainModel(movieDao.getMovieWithGenres(movieId))
    }

    override fun isMovieFavorite(movieId: Int): Flow<Boolean> {
        return movieDao.isMovieFavorite(movieId = movieId)
    }
}