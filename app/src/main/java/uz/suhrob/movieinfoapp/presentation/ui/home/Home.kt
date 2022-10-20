package uz.suhrob.movieinfoapp.presentation.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import uz.suhrob.movieinfoapp.presentation.ui.favorites.Favorites
import uz.suhrob.movieinfoapp.presentation.ui.movies.Movies
import uz.suhrob.movieinfoapp.presentation.ui.search.Search

interface Home {

    val childStack: Value<ChildStack<*, Child>>
    val currentTab: Value<Tab>

    val movies: Movies
    val search: Search
    val favorites: Favorites

    fun tabClick(tab: Tab)

    sealed class Child {
        class MoviesChild(val component: Movies) : Child()
        class SearchChild(val component: Search) : Child()
        class FavoritesChild(val component: Favorites) : Child()
    }
}

enum class Tab(val title: String, val icon: ImageVector) {
    Movies("Movies", Icons.Rounded.Home),
    Search("Search", Icons.Rounded.Search),
    Favorites("Favorites", Icons.Rounded.Favorite),
}