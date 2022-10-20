package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesScreen
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesScreen
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreen

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class,
    ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class,
)
@Composable
fun HomeScreen(
    home: Home,
) {
    val childStack by home.childStack.subscribeAsState()
    val currentTab by home.currentTab.subscribeAsState()

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = currentTab.title)
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            NavigationBar {
                Tab.values().forEach { tab ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title
                            )
                        },
                        label = { Text(text = tab.title) },
                        selected = currentTab == tab,
                        onClick = { home.tabClick(tab) },
                    )
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { paddingValues ->
        Children(stack = childStack, modifier = Modifier.padding(paddingValues)) {
            when (val child = it.instance) {
                is Home.Child.FavoritesChild -> FavoritesScreen(component = child.component)
                is Home.Child.MoviesChild -> MoviesScreen(component = child.component)
                is Home.Child.SearchChild -> SearchScreen(component = child.component)
            }
        }
    }
}