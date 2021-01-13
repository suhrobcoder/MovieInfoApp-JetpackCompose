package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.Loading
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    Scaffold(
        topBar = {
            SearchAppBar(viewModel)
        }
    ) {
        when (viewModel.movies.value) {
            is Resource.Success -> {
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    items(viewModel.movies.value.data ?: listOf()) { movie ->
                        MovieSearchItem(movie = movie) {
                            // TODO()
                        }
                    }
                }
            }
            is Resource.Loading -> {
                Loading()
            }
            is Resource.Error -> {
                Error(onRetry = { viewModel.search() })
            }
        }
    }
}

@Composable
fun SearchAppBar(viewModel: SearchViewModel) {
    TopAppBar(backgroundColor = MaterialTheme.colors.background, elevation = 0.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(end = 16.dp)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .clickable(onClick = { /* TODO */ })
                    .padding(8.dp)
            )
            SearchField(
                value = viewModel.query.value,
                onValueChange = { viewModel.onQueryChange(it) },
                onSearch = { viewModel.search() }
            )
        }
    }
}