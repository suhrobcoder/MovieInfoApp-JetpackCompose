package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.other.UiState
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(
    component: Search,
) {
    val state by component.state.subscribeAsState()
    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight(),
    ) {
        item {
            SearchField(
                value = state.query,
                onValueChange = { component.sendEvent(SearchEvent.QueryChange(it)) },
                onSearch = { component.sendEvent(SearchEvent.ExecuteSearch) },
                modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp),
            )
        }

        when (state.uiState) {
            UiState.success -> {
                items(state.movies) { movie ->
                    MovieSearchItem(movie = movie, onClick = { component.sendEvent(SearchEvent.MovieClick(movie)) })
                }
            }
            UiState.loading -> {
                items(10) {
                    SearchItemShimmer()
                }
            }
            UiState.error -> {
                item {
                    Error(onRetry = { component.sendEvent(SearchEvent.ExecuteSearch) })
                }
            }
        }
    }
}