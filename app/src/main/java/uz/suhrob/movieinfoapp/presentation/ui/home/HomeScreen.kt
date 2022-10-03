package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesScreen
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesScreen
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreen

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class
)
@Composable
fun HomeScreen(
    navigateToDetails: (Movie) -> Unit,
) {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = remember(currentDestination) {
        items.find { screen ->
            currentDestination?.hierarchy?.any { it.route == screen.route } == true
        }
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = currentScreen?.title ?: stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(text = screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Movies.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                Screen.Movies.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                MoviesScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetails = navigateToDetails,
                )
            }
            composable(
                Screen.Search.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                SearchScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetails = navigateToDetails,
                )
            }
            composable(
                Screen.Favorites.route,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                FavoritesScreen(
                    viewModel = hiltViewModel(),
                    navigateToDetails = navigateToDetails,
                )
            }
        }
    }
}

val items = listOf(Screen.Movies, Screen.Search, Screen.Favorites)

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Movies : Screen("movies", "Movies", Icons.Rounded.Home)
    object Search : Screen("search", "Search", Icons.Rounded.Search)
    object Favorites : Screen("favorites", "Favorites", Icons.Rounded.Favorite)
}