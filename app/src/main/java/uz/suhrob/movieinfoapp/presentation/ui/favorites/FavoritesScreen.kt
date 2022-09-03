package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    Scaffold(
        topBar = { FavoritesAppBar(navController = navController) },
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        MovieGrid(
            movies = movies.value,
            onChangeScrollPosition = { },
            onMovieClicked = { movie ->
                navController.navigate("details/${movie.id}")
            },
            onLastItemCreated = {},
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesAppBar(navController: NavController) {
    Column {
        Spacer(
            modifier = Modifier.fillMaxWidth().statusBarsPadding()
                .background(color = MaterialTheme.colorScheme.primary)
        )
        SmallTopAppBar(
            title = { Text(text = "Favorites") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            },
        )
    }
}