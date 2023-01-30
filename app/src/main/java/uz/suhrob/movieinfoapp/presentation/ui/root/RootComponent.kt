package uz.suhrob.movieinfoapp.presentation.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.coroutines.CoroutineScope
import kotlinx.parcelize.Parcelize
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.pref.MovieInfoDataStore
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepositoryImpl
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepositoryImpl
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsComponent
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeComponent

class RootComponent(
    componentContext: ComponentContext,
    private val ioScope: CoroutineScope,
    private val database: MovieDatabase,
    private val movieInfoDataStore: MovieInfoPref,
) : Root, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        initialStack = { listOf(Config.Home) },
        childFactory = ::child,
        handleBackButton = true,
    )
    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    private lateinit var favoritesRepository: FavoritesRepository
    private lateinit var movieRepository: MovieRepository

    private fun child(config: Config, componentContext: ComponentContext): Root.Child {
        if (!::favoritesRepository.isInitialized) {
            favoritesRepository = FavoritesRepositoryImpl(database.getMovieDao())
        }
        if (!::movieRepository.isInitialized) {
            movieRepository = MovieRepositoryImpl(ApiService(), movieInfoDataStore)
        }
        return when (config) {
            is Config.Details -> Root.Child.DetailsChild(
                DetailsComponent(
                    movie = config.movie,
                    ioScope = ioScope,
                    navigateBack = { navigation.pop() },
                    favoritesRepository = favoritesRepository,
                    movieRepository = movieRepository,
                )
            )
            Config.Home -> Root.Child.HomeChild(
                HomeComponent(
                    componentContext,
                    ioScope = ioScope,
                    favoritesRepository = favoritesRepository,
                    movieRepository = movieRepository,
                    navigateToDetails = { navigation.push(Config.Details(it)) },
                )
            )
        }
    }

    private sealed interface Config : Parcelable {
        @Parcelize
        object Home : Config

        @Parcelize
        class Details(val movie: Movie) : Config
    }
}