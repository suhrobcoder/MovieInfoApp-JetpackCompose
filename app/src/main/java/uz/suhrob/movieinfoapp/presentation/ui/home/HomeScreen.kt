package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import org.koin.androidx.compose.koinViewModel
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesScreen
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesViewModel
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesScreen
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesViewModel
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreen
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchViewModel

enum class Tab(val title: String, val icon: ImageVector) {
    Movies("Movies", Icons.Rounded.Home),
    Search("Search", Icons.Rounded.Search),
    Favorites("Favorites", Icons.Rounded.Favorite),
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun HomeScreen(
    onMovieClicked: (Movie) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(Tab.Movies) }

    val moviesViewModel: MoviesViewModel = koinViewModel()
    val searchViewModel: SearchViewModel = koinViewModel()
    val favoritesViewModel: FavoritesViewModel = koinViewModel()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text(text = selectedTab.title) },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                        label = { Text(text = tab.title) },
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                    )
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        when (selectedTab) {
            Tab.Movies -> MoviesScreen(
                viewModel = moviesViewModel,
                onMovieClicked = onMovieClicked,
                modifier = Modifier.padding(paddingValues),
            )
            Tab.Search -> SearchScreen(
                viewModel = searchViewModel,
                onMovieClicked = onMovieClicked,
                modifier = Modifier.padding(paddingValues),
            )
            Tab.Favorites -> FavoritesScreen(
                viewModel = favoritesViewModel,
                onMovieClicked = onMovieClicked,
                modifier = Modifier.padding(paddingValues),
            )
        }
    }
}
