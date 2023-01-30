package uz.suhrob.movieinfoapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uz.suhrob.movieinfoapp.data.local.dao.FakeMovieDao
import uz.suhrob.movieinfoapp.data.local.mapper.MovieEntityMapper
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val movieDao = FakeMovieDao()
    private lateinit var repository: FavoritesRepository

    private val testGenres = listOf(Genre(1, "Action"))
    private val testMovie = Movie(1, "title", "overview", "path", "path", testGenres, "", null, false, 1, 1.0)
    private val testGenresEntity = MovieEntityMapper.mapFromDomainModel(testMovie).genres
    private val testMovieEntity = MovieEntityMapper.mapFromDomainModel(testMovie).movie

    @Before
    fun setUp() {
        repository = FavoritesRepositoryImpl(movieDao)
    }

    @After
    fun clear() {
        movieDao.clear()
    }

    @Test
    fun insertMovieTest() = runTest {
        repository.insertMovie(testMovie)
        testGenresEntity.forEach {
            assertThat(it).isIn(movieDao.genres)
        }
        assertThat(testMovieEntity).isIn(movieDao.movies)
    }

    @Test
    fun deleteMovieTest() = runTest {
        movieDao.insertMovie(testMovieEntity)
        repository.deleteMovie(testMovie)
        assertThat(testMovieEntity).isNotIn(movieDao.movies)
    }

    @Test
    fun getAllMoviesTest() = runTest {
        movieDao.insertMovie(testMovieEntity)
        val movies = repository.getAllMovies().first()
        assertThat(testMovie.copy(genres = emptyList())).isIn(movies)
    }

    @Test
    fun getMovieTest() = runTest {
        repository.insertMovie(testMovie)
        val movie = repository.getMovie(1)
        assertThat(movie).isEqualTo(testMovie)
        assertThat(movie.genres).isEqualTo(testGenres)
    }

    @Test
    fun isMovieFavoriteTest() = runTest {
        movieDao.insertMovie(testMovieEntity)
        assertThat(repository.isMovieFavorite(1).first()).isEqualTo(true)
        movieDao.deleteMovie(testMovieEntity)
        assertThat(repository.isMovieFavorite(1).first()).isEqualTo(false)
    }
}