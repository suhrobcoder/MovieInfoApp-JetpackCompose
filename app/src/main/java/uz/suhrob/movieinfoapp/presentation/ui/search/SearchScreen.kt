package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    val query = viewModel.query.collectAsState()
    Scaffold(
        topBar = {
            SearchAppBar(query.value, viewModel, navController)
        },
    ) { paddingValues ->
        when (movies.value) {
            is Resource.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(top = 8.dp)
                ) {
                    items(movies.value.data ?: listOf()) { movie ->
                        MovieSearchItem(movie = movie) {
                            navController.navigate("details/${movie.id}")
                        }
                    }
                }
            }
            is Resource.Loading -> {
                Column {
                    repeat(10) {
                        SearchItemShimmer()
                    }
                }
            }
            is Resource.Error -> {
                Error(onRetry = { viewModel.onTriggerEvent(SearchEvent.Search) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(query: String, viewModel: SearchViewModel, navController: NavController) {
    Column {
        SmallTopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            title = {
                SearchField(
                    value = query,
                    onValueChange = { viewModel.onQueryChange(it) },
                    onSearch = { viewModel.onTriggerEvent(SearchEvent.Search) }
                )
            }
        )
    }
}