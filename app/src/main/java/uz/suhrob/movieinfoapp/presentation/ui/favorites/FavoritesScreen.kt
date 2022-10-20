package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    component: Favorites,
) {
    val state by component.state.subscribeAsState()
    MovieGrid(
        movies = state.movies,
        onChangeScrollPosition = { },
        onMovieClicked = { component.sendEvent(FavoritesEvent.MovieClick(it)) },
        onLastItemCreated = {},
        modifier = Modifier.fillMaxHeight(),
    )
}