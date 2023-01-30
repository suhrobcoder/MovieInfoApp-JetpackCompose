package uz.suhrob.movieinfoapp.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef
import uz.suhrob.movieinfoapp.data.local.mapper.GenreEntityMapper
import uz.suhrob.movieinfoapp.data.local.mapper.MovieEntityMapper
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getMovieDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovie() = runTest {
        val movie = Movie(1, "title", "overview", "path", "path", listOf(), "", 1, false, 1, 1.0)
        val movieEntity = MovieEntityMapper.mapFromDomainModel(movie).movie
        dao.insertMovie(movieEntity)

        val allMovies = dao.getAllMovies().first()

        assertThat(allMovies).contains(movieEntity)
    }

    @Test
    fun deleteMovie() = runTest {
        val movie = Movie(1, "title", "overview", "path", "path", listOf(), "", 1, false, 1, 1.0)
        val movieEntity = MovieEntityMapper.mapFromDomainModel(movie).movie
        dao.insertMovie(movieEntity)
        dao.deleteMovie(movieEntity)

        val allMovies = dao.getAllMovies().first()

        assertThat(allMovies).doesNotContain(movieEntity)
    }

    @Test
    fun insertMovieGenreCrossRef() = runTest {
        val genre1 = Genre(1, "genre1")
        val genre2 = Genre(2, "genre2")
        val genre3 = Genre(3, "genre3")
        val genres = GenreEntityMapper.mapFromList(listOf(genre1, genre2, genre3))

        val movie = Movie(1, "title", "overview", "path", "path", listOf(), "", 1, false, 1, 1.0)
        val movieEntity = MovieEntityMapper.mapFromDomainModel(movie).movie

        dao.insertGenres(genres)
        dao.insertMovie(movieEntity)
        dao.insertMovieGenreCrossRef(genres.map { MovieGenreCrossRef(movieEntity.id, it.id) })

        val movieWithGenres = dao.getMovieWithGenres(movieEntity.id)

        assertThat(movieWithGenres.movie).isEqualTo(movieEntity)
        assertThat(movieWithGenres.genres).isEqualTo(genres)
    }

    @Test
    fun isMovieFavorite() = runTest {
        val movie = Movie(1, "title", "overview", "path", "path", listOf(), "", 1, false, 1, 1.0)
        val movieEntity = MovieEntityMapper.mapFromDomainModel(movie).movie
        dao.insertMovie(movieEntity)

        val isFavorite = dao.isMovieFavorite(movieEntity.id).first()
        assertThat(isFavorite).isTrue()

        dao.deleteMovie(movieEntity)
        val isFavorite2 = dao.isMovieFavorite(movieEntity.id).first()
        assertThat(isFavorite2).isFalse()
    }
}