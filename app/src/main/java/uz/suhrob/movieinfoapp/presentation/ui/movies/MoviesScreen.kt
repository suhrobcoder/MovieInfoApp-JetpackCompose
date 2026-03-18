package uz.suhrob.movieinfoapp.presentation.ui.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.base.CollectEffects
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalFoundationApi
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effect) { effect ->
        when (effect) {
            is MoviesEffect.NavigateToDetails -> onMovieClicked(effect.movie)
        }
    }

    val configuration = LocalConfiguration.current
    val lazyListState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf { lazyListState.isScrolledToEnd() }
    }

    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    LaunchedEffect(endOfListReached) {
        viewModel.sendEvent(MoviesEvent.NextPage)
    }

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
    ) {
        item {
            CategoryRow(state.selectedCategory) { category ->
                viewModel.sendEvent(MoviesEvent.ChangeCategory(category))
            }
        }
        item {
            GenreRow(
                genres = state.genres,
                selectedGenre = state.selectedGenre,
                error = state.uiState == UiState.loading,
                shimmer = shimmer,
            ) { genre ->
                viewModel.sendEvent(MoviesEvent.SelectGenre(genre))
            }
        }
        gridItems(
            data = state.movies,
            columnCount = configuration.screenWidthDp / 150,
        ) { movie ->
            MovieItem(
                title = movie.title,
                imageUrl = getImageUrl(movie.posterPath),
                rating = movie.voteAverage,
                onClick = { viewModel.sendEvent(MoviesEvent.MovieClicked(movie)) }
            )
        }
        if (state.uiState == UiState.loading && state.movies.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        if (state.uiState == UiState.loading && state.movies.isEmpty()) {
            gridItems((1..4).toList(), columnCount = 2) {
                MovieItemShimmer(shimmer)
            }
        }
    }
}
