package uz.suhrob.movieinfoapp.presentation.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.other.Resource
import uz.suhrob.movieinfoapp.presentation.components.Error
import uz.suhrob.movieinfoapp.presentation.components.MovieSearchItem
import uz.suhrob.movieinfoapp.presentation.components.SearchField
import uz.suhrob.movieinfoapp.presentation.components.SearchItemShimmer
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen

object SearchScreen : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Search"
            val icon = rememberVectorPainter(image = Icons.Rounded.Search)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @ExperimentalCoroutinesApi
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent!!
        val screenModel = getScreenModel<SearchScreenModel>()
        val movies by screenModel.movies.collectAsState()
        val query by screenModel.query.collectAsState()
        LazyColumn {
            item {
                SearchField(
                    value = query,
                    onValueChange = { screenModel.onQueryChange(it) },
                    onSearch = { screenModel.onTriggerEvent(SearchEvent.Search) },
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp),
                )
            }
            when (movies) {
                is Resource.Success -> {
                    items(movies.data ?: listOf()) { movie ->
                        MovieSearchItem(movie = movie) {
                            navigator.push(DetailsScreen(movie.id))
                        }
                    }
                }
                is Resource.Loading -> {
                    item {
                        Column {
                            repeat(10) {
                                SearchItemShimmer()
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    item {
                        Error(onRetry = { screenModel.onTriggerEvent(SearchEvent.Search) })
                    }
                }
            }
        }
    }
}
