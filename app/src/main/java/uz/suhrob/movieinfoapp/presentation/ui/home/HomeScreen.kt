package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import uz.suhrob.movieinfoapp.domain.model.defaultGenre
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    Scaffold(
        topBar = { HomeAppBar(onSearchClick = { navController.navigate("search") }) }
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
            when (viewModel.movies.value) {
                is Resource.Success -> {
                    MovieGrid(
                        movies = viewModel.movies.value.data?.filter {
                            viewModel.selectedGenre.value == defaultGenre
                                    || it.genres.map { genre -> genre.name }
                                .contains(viewModel.selectedGenre.value.name)
                        } ?: listOf()
                    ) { movie ->
                        navController.navigate("details/${movie.id}")
                    }
                }
                is Resource.Loading -> {
                    MovieShimmerGrid()
                }
                is Resource.Error -> {
                    Error(onRetry = { viewModel.loadMovies() })
                }
            }
        }
    }
}

@Composable
fun HomeAppBar(onSearchClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Movie Info App")
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(imageVector = Icons.Filled.Search)
            }
        }
    )
}

@Preview
@Composable
fun PreviewHomeAppBar() {
    HomeAppBar(onSearchClick = { })
}