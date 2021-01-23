package uz.suhrob.movieinfoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.AuthInterceptor
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepositoryImpl
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepositoryImpl
import uz.suhrob.movieinfoapp.other.BASE_URL
import uz.suhrob.movieinfoapp.presentation.theme.MovieInfoAppTheme
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsViewModel
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesScreen
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesViewModel
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeScreen
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeViewModel
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreen
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchViewModel

@ExperimentalSerializationApi
@ExperimentalFoundationApi
class MainActivity : AppCompatActivity() {
    private lateinit var repository: MovieRepository
    private lateinit var favoritesRepository: FavoritesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(AuthInterceptor())
        val service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }.asConverterFactory(MediaType.get("application/json"))
            )
            .client(httpClient.build())
            .build()
            .create(ApiService::class.java)
        repository = MovieRepositoryImpl(service)
        val movieDao = MovieDatabase.getInstance(applicationContext).getMovieDao()
        favoritesRepository = FavoritesRepositoryImpl(movieDao)

        setContent {
            MovieInfoAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        val viewModel = viewModel(
                            modelClass = HomeViewModel::class.java,
                            factory = ViewModelFactory(repository)
                        )
                        HomeScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("search") {
                        val viewModel = viewModel(
                            modelClass = SearchViewModel::class.java,
                            factory = ViewModelFactory(repository)
                        )
                        SearchScreen(viewModel = viewModel, navController = navController)
                    }
                    composable(
                        "details/{id}",
                        arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
                    ) {
                        val id = it.arguments?.getInt("id") ?: 0
                        val viewModel = viewModel(
                            modelClass = DetailsViewModel::class.java,
                            factory = ViewModelFactory(repository, favoritesRepository, id)
                        )
                        DetailsScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("favorites") {
                        val viewModel = viewModel(
                            modelClass = FavoritesViewModel::class.java,
                            factory = FavoritesViewModelFactory(favoritesRepository)
                        )
                        FavoritesScreen(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}