package uz.suhrob.movieinfoapp.presentation.ui.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.CoroutineScope
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.favorites.Favorites
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesComponent
import uz.suhrob.movieinfoapp.presentation.ui.movies.Movies
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesComponent
import uz.suhrob.movieinfoapp.presentation.ui.search.Search
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchComponent

class HomeComponent(
    componentContext: ComponentContext,
    private val ioScope: CoroutineScope,
    private val movieRepository: MovieRepository,
    private val favoritesRepository: FavoritesRepository,
    private val navigateToDetails: (Movie) -> Unit,
) : Home, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Movies) },
        childFactory = ::child,
    )
    override val childStack: Value<ChildStack<*, Home.Child>> = stack

    override val currentTab: Value<Tab> = childStack.map {
        when (it.active.instance) {
            is Home.Child.FavoritesChild -> Tab.Favorites
            is Home.Child.MoviesChild -> Tab.Movies
            is Home.Child.SearchChild -> Tab.Search
        }
    }

    override lateinit var movies: Movies
    override lateinit var search: Search
    override lateinit var favorites: Favorites

    private fun child(config: Config, componentContext: ComponentContext): Home.Child {
        return when (config) {
            Config.Movies -> {
                if (!::movies.isInitialized) {
                    movies = MoviesComponent(
                        ioScope = ioScope,
                        navigateToDetails = navigateToDetails,
                        repository = movieRepository,
                    )
                }
                Home.Child.MoviesChild(movies)
            }
            Config.Search -> {
                if (!::search.isInitialized) {
                    search = SearchComponent(
                        ioScope = ioScope,
                        repository = movieRepository,
                        navigateToDetails = navigateToDetails,
                    )
                }
                Home.Child.SearchChild(search)
            }
            Config.Favorites -> {
                if (!::favorites.isInitialized) {
                    favorites = FavoritesComponent(
                        ioScope = ioScope,
                        repository = favoritesRepository,
                        navigateToDetails = navigateToDetails,
                    )
                }
                Home.Child.FavoritesChild(favorites)
            }
        }
    }

    override fun tabClick(tab: Tab) {
        navigation.bringToFront(
            when (tab) {
                Tab.Movies -> Config.Movies
                Tab.Search -> Config.Search
                Tab.Favorites -> Config.Favorites
            }
        )
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Movies : Config

        @Parcelize
        object Search : Config

        @Parcelize
        object Favorites : Config
    }
}