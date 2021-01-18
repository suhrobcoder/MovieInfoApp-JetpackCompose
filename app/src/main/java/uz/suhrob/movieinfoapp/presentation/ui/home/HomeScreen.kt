package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
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
                viewModel.changeCategory(category)
            }
            GenreRow(
                genres = viewModel.genres.value,
                selectedGenre = viewModel.selectedGenre.value
            ) { genre ->
                viewModel.selectedGenre.value = genre
            }
            Box(contentAlignment = Alignment.Center) {
                MovieGrid(
                    movies = viewModel.movies.value.filter {
                        viewModel.selectedGenre.value == defaultGenre
                                || it.genres.map { genre -> genre.name }
                            .contains(viewModel.selectedGenre.value.name)
                    },
                    onChangeScrollPosition = { index ->
                        viewModel.onChangeScrollPosition(index)
                        if ((index + 1) >= (viewModel.currentPage.value * PAGE_SIZE) && !viewModel.loading.value) {
                            viewModel.nextPage()
                        }
                    }
                ) { movie ->
                    navController.navigate("details/${movie.id}")
                }
                if (viewModel.loading.value) {
                    if (viewModel.movies.value.isEmpty()) {
                        MovieShimmerGrid()
                    } else {
                        Loading()
                    }
                }
                if (viewModel.error.value && viewModel.movies.value.isEmpty()) {
                    Error(onRetry = { viewModel.loadMovies() })
                }
            }
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
    DrawerItem(icon = Icons.Filled.Search, title = "Search") {}
    DrawerItem(icon = Icons.Filled.Star, title = "Favorites") {}
}