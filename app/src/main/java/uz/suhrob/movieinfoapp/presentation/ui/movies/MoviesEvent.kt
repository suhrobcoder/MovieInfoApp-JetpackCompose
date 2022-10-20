package uz.suhrob.movieinfoapp.presentation.ui.movies

import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.components.Category

sealed interface MoviesEvent {
    data class ChangeCategory(val category: Category) : MoviesEvent
    object LoadGenres : MoviesEvent
    object LoadMovies : MoviesEvent
    object NextPage : MoviesEvent
    data class SelectGenre(val genre: Genre) : MoviesEvent
    data class MovieClicked(val movie: Movie) : MoviesEvent
}