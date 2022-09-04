package uz.suhrob.movieinfoapp.presentation.ui.movies

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.components.*
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen

object MoviesScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Home"
            val icon = rememberVectorPainter(image = Icons.Rounded.Home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    @ExperimentalCoroutinesApi
    @ExperimentalFoundationApi
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent!!
        val screenModel = getScreenModel<MoviesScreenModel>()
        val movies by screenModel.movies.collectAsState()
        val category by screenModel.category.collectAsState()
        val genres by screenModel.genres.collectAsState()
        val selectedGenre by screenModel.selectedGenre.collectAsState()

        val error by screenModel.error.collectAsState()
        val loading by screenModel.loading.collectAsState()

        val configuration = LocalConfiguration.current
        val lazyListState = rememberLazyListState()

        val endOfListReached by remember {
            derivedStateOf {
                lazyListState.isScrolledToEnd()
            }
        }

        LaunchedEffect(endOfListReached) {
            screenModel.onTriggerEvent(MoviesEvent.NextPage)
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            state = lazyListState,
        ) {
            item {
                CategoryRow(category) { category ->
                    screenModel.onTriggerEvent(MoviesEvent.ChangeCategory(category))
                }
            }
            item {
                GenreRow(
                    genres = genres,
                    selectedGenre = selectedGenre,
                    error = error,
                ) { genre ->
                    screenModel.setSelectedGenre(genre)
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
                    onClick = { navigator.push(DetailsScreen(movie.id)) }
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
                    MovieItemShimmer()
                }
            }
        }
    }
}
