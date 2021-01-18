package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, navController: NavController) {
    Scaffold(topBar = { FavoritesAppBar(navController = navController) }) {
        MovieGrid(
            movies = viewModel.movies.value,
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