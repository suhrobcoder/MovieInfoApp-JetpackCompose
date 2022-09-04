package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.components.MovieGrid
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreen

object FavoritesScreen : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Favorites"
            val icon = rememberVectorPainter(image = Icons.Rounded.Favorite)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    @ExperimentalCoroutinesApi
    @ExperimentalFoundationApi
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent!!
        val screenModel = getScreenModel<FavoritesScreenModel>()
        val movies = screenModel.movies.collectAsState()

        MovieGrid(
            movies = movies.value,
            onChangeScrollPosition = { },
            onMovieClicked = { movie ->
                navigator.push(DetailsScreen(movie.id))
            },
            onLastItemCreated = {},
        )
    }
}