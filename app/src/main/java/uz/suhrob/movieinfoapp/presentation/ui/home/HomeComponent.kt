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
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.favorites.Favorites
import uz.suhrob.movieinfoapp.presentation.ui.movies.Movies
import uz.suhrob.movieinfoapp.presentation.ui.search.Search

class HomeComponent(
    componentContext: ComponentContext,
    private val navigateToDetails: (Movie) -> Unit,
) : Home, ComponentContext by componentContext, KoinComponent {

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

    private fun child(config: Config, componentContext: ComponentContext): Home.Child {
        return when (config) {
            Config.Movies -> {
                Home.Child.MoviesChild(get<Movies>(parameters = { parametersOf(navigateToDetails) }))
            }
            Config.Search -> {
                Home.Child.SearchChild(get<Search>(parameters = { parametersOf(navigateToDetails) }))
            }
            Config.Favorites -> {
                Home.Child.FavoritesChild(get<Favorites>(parameters = { parametersOf(navigateToDetails) }))
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
        data object Movies : Config

        @Parcelize
        data object Search : Config

        @Parcelize
        data object Favorites : Config
    }
}