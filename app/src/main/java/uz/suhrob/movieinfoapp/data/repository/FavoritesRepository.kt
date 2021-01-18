package uz.suhrob.movieinfoapp.data.repository

import kotlinx.coroutines.flow.Flow
import uz.suhrob.movieinfoapp.domain.model.Movie

interface FavoritesRepository {
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovie(movie: Movie)
    fun getAllMovies(): Flow<List<Movie>>
    suspend fun getMovie(movieId: Int): Movie
    fun isMovieFavorite(movieId: Int): Flow<Boolean>
}