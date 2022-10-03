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
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    navigateToDetails: (Movie) -> Unit,
) {
    val movies by viewModel.movies.collectAsState()
    val category by viewModel.category.collectAsState()
    val genres by viewModel.genres.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    val error by viewModel.error.collectAsState()
    val loading by viewModel.loading.collectAsState()

    val configuration = LocalConfiguration.current
    val lazyListState = rememberLazyListState()

    val endOfListReached by remember {
        derivedStateOf {
            lazyListState.isScrolledToEnd()
        }
    }

    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    LaunchedEffect(endOfListReached) {
        viewModel.onTriggerEvent(MoviesEvent.NextPage)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = lazyListState,
    ) {
        item {
            CategoryRow(category) { category ->
                viewModel.onTriggerEvent(MoviesEvent.ChangeCategory(category))
            }
        }
        item {
            GenreRow(
                genres = genres,
                selectedGenre = selectedGenre,
                error = error,
                shimmer = shimmer,
            ) { genre ->
                viewModel.setSelectedGenre(genre)
            }
        }
        gridItems(
            data = movies,
            columnCount = configuration.screenWidthDp / 150,
        ) { movie ->
            MovieItem(
                title = movie.title,
                imageUrl = getImageUrl(movie.posterPath),
                rating = movie.voteAverage,
                onClick = { navigateToDetails(movie) }
            )
        }
        if (loading && movies.isNotEmpty()) {
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
        if (loading && movies.isEmpty()) {
            gridItems(
                (1..4).toList(),
                columnCount = 2,
            ) {
                MovieItemShimmer(shimmer)
            }
        }
    }
}
