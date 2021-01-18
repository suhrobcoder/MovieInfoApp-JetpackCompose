package uz.suhrob.movieinfoapp.data.local.dao

import androidx.room.*
import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieWithGenre

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieGenreCrossRef(movieGenres: List<MovieGenreCrossRef>)

    @Transaction
    @Query("SELECT * FROM movies WHERE movie_id = :movieId")
    suspend fun getMovieWithGenres(movieId: Int): MovieWithGenre
}