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
import kotlinx.serialization.ExperimentalSerializationApi
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieInfoAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        val viewModel = viewModel(modelClass = HomeViewModel::class.java)
                        HomeScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("search") {
                        val viewModel = viewModel(modelClass = SearchViewModel::class.java)
                        SearchScreen(viewModel = viewModel, navController = navController)
                    }
                    composable(
                        "details/{id}",
                        arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
                    ) {
                        val id = it.arguments?.getInt("id") ?: 0
                        val viewModel = viewModel(modelClass = DetailsViewModel::class.java)
                        viewModel.movieId = id
                        DetailsScreen(viewModel = viewModel, navController = navController)
                    }
                    composable("favorites") {
                        val viewModel = viewModel(modelClass = FavoritesViewModel::class.java)
                        FavoritesScreen(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}