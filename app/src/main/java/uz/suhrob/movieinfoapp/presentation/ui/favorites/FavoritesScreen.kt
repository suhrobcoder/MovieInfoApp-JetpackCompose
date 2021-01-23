package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    Scaffold(topBar = { FavoritesAppBar(navController = navController) }) {
        MovieGrid(
            movies = movies.value,
            onChangeScrollPosition = { },
            onMovieClicked = { movie ->
                navController.navigate("details/${movie.id}")
            }
        )
    }
}

@Composable
fun FavoritesAppBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Favorites") },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack)
            }
        }
    )
}