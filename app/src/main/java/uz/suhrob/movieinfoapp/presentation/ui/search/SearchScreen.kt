package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.base.CollectEffects
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effect) { effect ->
        when (effect) {
            is SearchEffect.NavigateToDetails -> onMovieClicked(effect.movie)
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxHeight(),
    ) {
        item {
            SearchField(
                value = state.query,
                onValueChange = { viewModel.sendEvent(SearchEvent.QueryChange(it)) },
                onSearch = { viewModel.sendEvent(SearchEvent.ExecuteSearch) },
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp),
            )
        }

        when (state.uiState) {
            UiState.success -> {
                items(state.movies) { movie ->
                    MovieSearchItem(
                        movie = movie,
                        onClick = { viewModel.sendEvent(SearchEvent.MovieClick(movie)) },
                    )
                }
            }
            UiState.loading -> {
                items(10) { SearchItemShimmer() }
            }
            UiState.error -> {
                item {
                    Error(onRetry = { viewModel.sendEvent(SearchEvent.ExecuteSearch) })
                }
            }
        }
    }
}
