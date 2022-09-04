package uz.suhrob.movieinfoapp.di

import org.koin.dsl.module
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsScreenModel
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesScreenModel
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesScreenModel
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchScreenModel

val screenModelModule = module {
    factory { MoviesScreenModel(get()) }
    factory { SearchScreenModel(get()) }
    factory { FavoritesScreenModel(get()) }
    factory { DetailsScreenModel(get(), get()) }
}