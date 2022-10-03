package uz.suhrob.movieinfoapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import uz.suhrob.movieinfoapp.presentation.theme.MovieInfoAppTheme
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsViewModel
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeScreen

@ExperimentalAnimationApi
@ExperimentalSerializationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MovieInfoApp()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun MovieInfoApp() {
    MovieInfoAppTheme {
        val navController = rememberAnimatedNavController()
        AnimatedNavHost(navController = navController, startDestination = "home") {
            composable(
                "home",
                enterTransition = {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(100))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(100))
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(100))
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(100))
                },
            ) {
                HomeScreen(navigateToDetails = { movie -> navController.navigate("details/${movie.id}") })
            }
            composable(
                "details/{id}",
                arguments = listOf(navArgument(name = "id") { type = NavType.IntType }),
                enterTransition = {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.Left, tween(100))
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, tween(100))
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentScope.SlideDirection.Right, tween(100))
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, tween(100))
                },
            ) {
                val id = it.arguments?.getInt("id") ?: 0
                val viewModel = hiltViewModel<DetailsViewModel>()
                viewModel.movieId = id
                DetailsScreen(viewModel = viewModel, navigateBack = navController::popBackStack)
            }
        }
    }
}