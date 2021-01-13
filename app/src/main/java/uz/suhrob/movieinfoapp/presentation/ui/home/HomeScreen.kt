package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.*

@ExperimentalFoundationApi
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Scaffold {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryRow(viewModel.category.value) { category ->
                viewModel.changeCategory(category)
            }
            GenreRow(
                genres = viewModel.genres.value,
                selectedGenre = viewModel.selectedGenre.value
            ) { genre ->

            }
            when (viewModel.movies.value) {
                is Resource.Success -> {
                    MovieGrid(movies = viewModel.movies.value.data ?: listOf()) {

                    }
                }
                is Resource.Loading -> {
                    Loading()
                }
                is Resource.Error -> {
                    Error(onRetry = { viewModel.loadMovies() })
                }
            }
        }
    }
}