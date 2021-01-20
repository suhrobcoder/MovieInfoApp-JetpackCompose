package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import uz.suhrob.movieinfoapp.R
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.PAGE_SIZE
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = {
            HomeAppBar(
                onNavigationClick = { scaffoldState.drawerState.open() },
                onSearchClick = { navController.navigate("search") })
        },
        drawerContent = { HomeDrawer(navController = navController) },
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryRow(viewModel.category.value) { category ->
                viewModel.onTriggerEvent(HomeEvent.ChangeCategory(category))
            }
            GenreRow(
                genres = viewModel.genres.value,
                selectedGenre = viewModel.selectedGenre.value
            ) { genre ->
                viewModel.selectedGenre.value = genre
            }
            HomeBody(
                navController = navController,
                movies = viewModel.movies.value,
                selectedGenre = viewModel.selectedGenre.value,
                onChangeScrollPosition = { viewModel.onChangeScrollPosition(it) },
                triggerEvent = { viewModel.onTriggerEvent(it) },
                currentPage = viewModel.currentPage.value,
                loading = viewModel.loading.value,
                error = viewModel.error.value
            )
        }
    }
}

@Composable
fun HomeAppBar(onNavigationClick: () -> Unit, onSearchClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Movie Info App")
        },
        navigationIcon = {
            IconButton(onClick = onNavigationClick) {
                Icon(imageVector = Icons.Filled.Menu)
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Filled.Search)
            }
        }
    )
}

@Composable
fun HomeDrawer(navController: NavController) {
    DrawerHeader()
    Divider()
    DrawerItem(icon = Icons.Filled.Search, title = "Search") { navController.navigate("search") }
    DrawerItem(icon = vectorResource(id = R.drawable.ic_heart), title = "Favorites") {
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
    currentPage: Int,
    loading: Boolean,
    error: Boolean,
) {
    Box(contentAlignment = Alignment.Center) {
        MovieGrid(
            movies = movies.filter {
                selectedGenre == defaultGenre
                        || it.genres.map { genre -> genre.name }
                    .contains(selectedGenre.name)
            },
            onChangeScrollPosition = { index ->
                onChangeScrollPosition(index)
                if ((index + 1) >= (currentPage * PAGE_SIZE) && !loading) {
                    triggerEvent(HomeEvent.NextPage)
                }
            }
        ) { movie ->
            navController.navigate("details/${movie.id}")
        }
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