package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.base.CollectEffects
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid

@ExperimentalFoundationApi
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effect) { effect ->
        when (effect) {
            is FavoritesEffect.NavigateToDetails -> onMovieClicked(effect.movie)
        }
    }

    MovieGrid(
        movies = state.movies,
        onChangeScrollPosition = { },
        onMovieClicked = { viewModel.sendEvent(FavoritesEvent.MovieClick(it)) },
        onLastItemCreated = {},
        modifier = modifier.fillMaxHeight(),
    )
}
