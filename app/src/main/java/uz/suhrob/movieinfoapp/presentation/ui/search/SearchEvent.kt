package uz.suhrob.movieinfoapp.presentation.ui.search

import uz.suhrob.movieinfoapp.domain.model.Movie

sealed interface SearchEvent {
    data class QueryChange(val query: String) : SearchEvent
    data class MovieClick(val movie: Movie) : SearchEvent
    object ExecuteSearch : SearchEvent
}