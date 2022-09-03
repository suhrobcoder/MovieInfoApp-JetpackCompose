package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.other.getImageUrl
import uz.suhrob.movieinfoapp.presentation.components.*

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
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

    LaunchedEffect(endOfListReached) {
        viewModel.onTriggerEvent(HomeEvent.NextPage)
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { navController.navigate("search") }) {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            state = lazyListState,
        ) {
            item {
                CategoryRow(category) { category ->
                    viewModel.onTriggerEvent(HomeEvent.ChangeCategory(category))
                }
            }
            item {
                GenreRow(
                    genres = genres,
                    selectedGenre = selectedGenre,
                    error = error,
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
                    onClick = { navController.navigate("details/${movie.id}") }
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
