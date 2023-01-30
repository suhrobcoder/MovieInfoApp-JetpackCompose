package uz.suhrob.movieinfoapp.data.local.dao

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieWithGenre

class FakeMovieDao: MovieDao {

    val movies = mutableListOf<MovieEntity>()
    val genres = mutableListOf<GenreEntity>()
    private val relations = mutableListOf<MovieGenreCrossRef>()

    fun clear() {
        movies.clear()
        genres.clear()
        relations.clear()
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        movies.add(movie)
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        movies.remove(movie)
    }

    override suspend fun insertGenres(genres: List<GenreEntity>) {
        this.genres.addAll(genres)
    }

    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return flowOf(movies)
    }

    override suspend fun insertMovieGenreCrossRef(movieGenres: List<MovieGenreCrossRef>) {
        relations.addAll(movieGenres)
    }

    override suspend fun getMovieWithGenres(movieId: Int): MovieWithGenre {
        val movie = movies.first { it.id == movieId }
        val rel = relations.filter { it.movieId == movieId }
        val genres = genres.filter { genre -> rel.any { it.genreId == genre.id } }
        return MovieWithGenre(movie, genres)
    }

    override fun isMovieFavorite(movieId: Int): Flow<Boolean> {
        return flowOf(movies.any { it.id == movieId })
    }
}