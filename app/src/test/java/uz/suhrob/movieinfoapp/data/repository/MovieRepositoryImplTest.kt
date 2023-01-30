package uz.suhrob.movieinfoapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import retrofit2.Response
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.response.GuestSessionResponse
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository

@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var api: ApiService
    private lateinit var pref: MovieInfoPref
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        api = Mockito.mock(ApiService::class.java)
        pref = Mockito.mock(MovieInfoPref::class.java)
        repository = MovieRepositoryImpl(api, pref)
    }

    @Test
    fun getGenresTest() = runTest {
        repository.getGenres()
        Mockito.verify(api).getGenres()
    }

    @Test
    fun getMovieTest() = runTest {
        repository.getMovie(1)
        Mockito.verify(api).getMovie(1)
    }

    @Test
    fun getMovieReviewsTest() = runTest {
        repository.getMovieReviews(1, 1)
        Mockito.verify(api).getMovieReviews(1, 1)
    }

    @Test
    fun getMovieVideosTest() = runTest {
        repository.getMovieVideos(1)
        Mockito.verify(api).getMovieVideos(1)
    }

    @Test
    fun getPopularMoviesTest() = runTest {
        repository.getPopularMovies(1)
        Mockito.verify(api).getPopularMovies(1)
    }

    @Test
    fun getTopRatedMoviesTest() = runTest {
        repository.getTopRatedMovies(1)
        Mockito.verify(api).getTopRatedMovies(1)
    }

    @Test
    fun getUpcomingMoviesTest() = runTest {
        repository.getUpcomingMovies(1)
        Mockito.verify(api).getUpcomingMovies(1)
    }

    @Test
    fun searchMoviesTest() = runTest {
        repository.searchMovies("", 1)
        Mockito.verify(api).searchMovies("", 1)
    }

    @Test
    fun getMovieCreditsTest() = runTest {
        repository.getMovieCredits(1)
        Mockito.verify(api).getMovieCredits(1)
    }

    @Test
    fun rateMovie_NoSessionId_Test() = runTest {
        `when`(pref.guestSessionId).thenReturn(flowOf(null))
        `when`(api.createGuestSession()).thenReturn(Response.success(GuestSessionResponse(true, "id", "")))
        repository.rateMovie(1, 1)
        Mockito.verify(api).createGuestSession()
    }

    @Test
    fun rateMovie_WithSessionId_Test() = runTest {
        `when`(pref.guestSessionId).thenReturn(flowOf("id"))
        `when`(api.createGuestSession()).thenReturn(Response.success(GuestSessionResponse(true, "id", "")))
        repository.rateMovie(1, 1)
        Mockito.verify(api, times(0)).createGuestSession()
    }
}