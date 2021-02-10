package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    Scaffold(
        topBar = { FavoritesAppBar(navController = navController) },
        modifier = Modifier.navigationBarsPadding()
    ) {
        MovieGrid(
            movies = movies.value,
            onChangeScrollPosition = { },
            onMovieClicked = { movie ->
                navController.navigate("details/${movie.id}")
            },
            onLastItemCreated = {}
        )
    }
}

@Composable
fun FavoritesAppBar(navController: NavController) {
    Column {
        Spacer(
            modifier = Modifier.fillMaxWidth().statusBarsHeight()
                .background(color = MaterialTheme.colors.primary)
        )
        TopAppBar(
            title = { Text(text = "Favorites") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
            elevation = 0.dp
        )
    }
}