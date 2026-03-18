package uz.suhrob.movieinfoapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsViewModel
import uz.suhrob.movieinfoapp.presentation.ui.favorites.FavoritesViewModel
import uz.suhrob.movieinfoapp.presentation.ui.movies.MoviesViewModel
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchViewModel

val viewModelModule = module {
    viewModelOf(::MoviesViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoritesViewModel)
    // DetailsViewModel takes a runtime movieId param, so it uses the parametrized form
    viewModel { (movieId: Int) ->
        DetailsViewModel(movieId, get(), get())
    }
}
