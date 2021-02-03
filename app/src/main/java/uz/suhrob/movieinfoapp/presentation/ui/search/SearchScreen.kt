package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(viewModel: SearchViewModel, navController: NavController) {
    val movies = viewModel.movies.collectAsState()
    val query = viewModel.query.collectAsState()
    Scaffold(
        topBar = {
            SearchAppBar(query.value, viewModel, navController)
        },
        modifier = Modifier.navigationBarsPadding()
    ) {
        when (movies.value) {
            is Resource.Success -> {
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
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

@Composable
fun SearchAppBar(query: String, viewModel: SearchViewModel, navController: NavController) {
    Column {
        Spacer(
            modifier = Modifier.fillMaxWidth().statusBarsHeight()
                .background(color = MaterialTheme.colors.primary)
        )
        TopAppBar(elevation = 0.dp) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(percent = 50))
                        .clickable(onClick = { navController.popBackStack() })
                        .padding(8.dp),
                    contentDescription = null
                )
                SearchField(
                    value = query,
                    onValueChange = { viewModel.onQueryChange(it) },
                    onSearch = { viewModel.onTriggerEvent(SearchEvent.Search) }
                )
            }
        }
    }
}