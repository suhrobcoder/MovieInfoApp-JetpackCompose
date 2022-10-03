package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navigateToDetails: (Movie) -> Unit,
) {
    val movies = viewModel.movies.collectAsState()
    MovieGrid(
        movies = movies.value,
        onChangeScrollPosition = { },
        onMovieClicked = navigateToDetails,
        onLastItemCreated = {},
        modifier = Modifier.fillMaxHeight(),
    )
}