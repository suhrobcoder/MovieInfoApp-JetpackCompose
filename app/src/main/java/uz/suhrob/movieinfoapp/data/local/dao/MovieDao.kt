package uz.suhrob.movieinfoapp.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieWithGenre

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRef(movieGenres: List<MovieGenreCrossRef>)

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    suspend fun getMovieWithGenres(movieId: Int): MovieWithGenre

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE movie_id = :movieId)")
    fun isMovieFavorite(movieId: Int): Flow<Boolean>
}