package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    val category = viewModel.category.collectAsState()
    val genres = viewModel.genres.collectAsState()
    val selectedGenre = viewModel.selectedGenre.collectAsState()

    val error = viewModel.error.collectAsState()
    val loading = viewModel.loading.collectAsState()

    val composeScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            HomeAppBar(
                onNavigationClick = { composeScope.launch { scaffoldState.drawerState.open() } },
                onSearchClick = { navController.navigate("search") })
        },
        drawerContent = { HomeDrawer(navController = navController) },
        scaffoldState = scaffoldState,
        modifier = Modifier.navigationBarsPadding()
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            CategoryRow(category.value) { category ->
                viewModel.onTriggerEvent(HomeEvent.ChangeCategory(category))
            }
            GenreRow(
                genres = genres.value,
                selectedGenre = selectedGenre.value,
                error = error.value
            ) { genre ->
                viewModel.setSelectedGenre(genre)
            }
            HomeBody(
                navController = navController,
                movies = movies.value,
                selectedGenre = selectedGenre.value,
                onChangeScrollPosition = { viewModel.onChangeScrollPosition(it) },
                triggerEvent = { viewModel.onTriggerEvent(it) },
                loading = loading.value,
                error = error.value
            )
        }
    }
}

@Composable
fun HomeAppBar(onNavigationClick: () -> Unit, onSearchClick: () -> Unit) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(color = MaterialTheme.colors.primary)
        )
        TopAppBar(
            title = {
                Text(text = "Movie Info App")
            },
            navigationIcon = {
                IconButton(onClick = onNavigationClick) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            },
            actions = {
                IconButton(onClick = onSearchClick) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                }
            },
            elevation = 0.dp
        )
    }
}

@Composable
fun HomeDrawer(navController: NavController) {
    DrawerHeader()
    Divider()
    DrawerItem(icon = Icons.Filled.Search, title = "Search") { navController.navigate("search") }
    DrawerItem(icon = ImageVector.vectorResource(id = R.drawable.ic_heart), title = "Favorites") {
        navController.navigate("favorites")
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeBody(
    navController: NavController,
    movies: List<Movie>,
    selectedGenre: Genre,
    onChangeScrollPosition: (Int) -> Unit,
    triggerEvent: (HomeEvent) -> Unit,
    loading: Boolean,
    error: Boolean,
) {
    Box(contentAlignment = Alignment.Center) {
        MovieGrid(
            movies = movies.filter {
                selectedGenre == defaultGenre
                        || it.genres.map { genre -> genre.id }
                    .contains(selectedGenre.id)
            },
            onChangeScrollPosition = { index ->
                onChangeScrollPosition(index)
            },
            onLastItemCreated = {
                triggerEvent(HomeEvent.NextPage)
            },
            onMovieClicked = { movie ->
            navController.navigate("details/${movie.id}")
            }
        )
        if (loading) {
            if (movies.isEmpty()) {
                MovieShimmerGrid()
            } else {
                Loading()
            }
        }
        if (error && movies.isEmpty()) {
            Error(onRetry = { triggerEvent(HomeEvent.LoadMovies) })
        }
    }
}