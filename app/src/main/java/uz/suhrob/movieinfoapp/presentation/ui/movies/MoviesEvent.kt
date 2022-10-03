package uz.suhrob.movieinfoapp.presentation.ui.movies

import uz.suhrob.movieinfoapp.presentation.components.Category

sealed class MoviesEvent {
    data class ChangeCategory(val category: Category): MoviesEvent()
    object LoadGenres: MoviesEvent()
    object LoadMovies: MoviesEvent()
    object NextPage: MoviesEvent()
}