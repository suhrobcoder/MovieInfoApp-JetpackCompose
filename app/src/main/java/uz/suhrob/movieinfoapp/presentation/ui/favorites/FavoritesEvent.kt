package uz.suhrob.movieinfoapp.presentation.ui.favorites

import uz.suhrob.movieinfoapp.domain.model.Movie

sealed interface FavoritesEvent {
    data class MovieClick(val movie: Movie): FavoritesEvent
}