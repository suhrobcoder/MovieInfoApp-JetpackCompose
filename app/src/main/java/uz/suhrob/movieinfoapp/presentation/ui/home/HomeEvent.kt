package uz.suhrob.movieinfoapp.presentation.ui.home

import uz.suhrob.movieinfoapp.presentation.components.Category

sealed class HomeEvent {
    data class ChangeCategory(val category: Category): HomeEvent()
    object LoadGenres: HomeEvent()
    object LoadMovies: HomeEvent()
}