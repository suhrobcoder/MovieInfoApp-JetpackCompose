package uz.suhrob.movieinfoapp.di

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository
import uz.suhrob.movieinfoapp.presentation.ui.details.Details
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsComponent
import uz.suhrob.movieinfoapp.presentation.ui.favorites.Favorites
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesComponent
import uz.suhrob.movieinfoapp.presentation.ui.home.Home
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeComponent
import uz.suhrob.movieinfoapp.presentation.ui.movies.Movies
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesComponent
import uz.suhrob.movieinfoapp.presentation.ui.root.Root
import uz.suhrob.movieinfoapp.presentation.ui.root.RootComponent
import uz.suhrob.movieinfoapp.presentation.ui.search.Search
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchComponent

val componentModule = module {
    factory<Root> { (componentContext: ComponentContext) -> RootComponent(componentContext) }
    factory<Home> { (componentContext: ComponentContext, navigateToDetails: (Movie) -> Unit) -> HomeComponent(
        componentContext,
        navigateToDetails,
    ) }
    factory<Movies> { (navigateToDetails: (Movie) -> Unit) -> MoviesComponent(
        get<MovieRepository>(),
        get<CoroutineScope>(named("ioScope")),
        navigateToDetails,
    ) }
    factory<Search> { (navigateToDetails: (Movie) -> Unit) -> SearchComponent(
        get<MovieRepository>(),
        get<CoroutineScope>(named("ioScope")),
        navigateToDetails,
    ) }
    factory<Favorites> { (navigateToDetails: (Movie) -> Unit) -> FavoritesComponent(
        get<FavoritesRepository>(),
        get<CoroutineScope>(named("ioScope")),
        navigateToDetails,
    ) }
    factory<Details> { (movie: Movie, navigateBack: () -> Unit) -> DetailsComponent(
        movie,
        get<MovieRepository>(),
        get<FavoritesRepository>(),
        get<CoroutineScope>(named("ioScope")),
        navigateBack,
    ) }
}