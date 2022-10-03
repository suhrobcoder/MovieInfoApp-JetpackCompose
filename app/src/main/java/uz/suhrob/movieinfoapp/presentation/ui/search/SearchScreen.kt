package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navigateToDetails: (Movie) -> Unit,
) {
    val movies by viewModel.movies.collectAsState()
    val query by viewModel.query.collectAsState()
    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight(),
    ) {
        item {
            SearchField(
                value = query,
                onValueChange = viewModel::onQueryChange,
                onSearch = { viewModel.onTriggerEvent(SearchEvent.Search) },
                modifier = Modifier.padding(horizontal = 16.dp),
            )
        }

        when (movies) {
            is Resource.Success -> {
                items(movies.data ?: listOf()) { movie ->
                    MovieSearchItem(movie = movie, onClick = { navigateToDetails(movie) })
                }
            }
            is Resource.Loading -> {
                items(10) {
                    SearchItemShimmer()
                }
            }
            is Resource.Error -> {
                item {
                    Error(onRetry = { viewModel.onTriggerEvent(SearchEvent.Search) })
                }
            }
        }
    }
}