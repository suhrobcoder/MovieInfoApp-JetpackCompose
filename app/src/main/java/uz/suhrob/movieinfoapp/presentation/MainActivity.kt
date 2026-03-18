package uz.suhrob.movieinfoapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import uz.suhrob.movieinfoapp.presentation.navigation.DetailsRoute
import uz.suhrob.movieinfoapp.presentation.navigation.HomeRoute
import uz.suhrob.movieinfoapp.presentation.theme.MovieInfoAppTheme
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsViewModel
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MovieInfoAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeRoute,
                ) {
                    composable<HomeRoute> {
                        HomeScreen(
                            onMovieClicked = { movie ->
                                navController.navigate(DetailsRoute(movie.id))
                            }
                        )
                    }
                    composable<DetailsRoute> { backStackEntry ->
                        val route: DetailsRoute = backStackEntry.toRoute()
                        val viewModel: DetailsViewModel = koinViewModel(
                            parameters = { parametersOf(route.movieId) }
                        )
                        DetailsScreen(
                            viewModel = viewModel,
                            onBack = { navController.popBackStack() },
                        )
                    }
                }
            }
        }
    }
}
