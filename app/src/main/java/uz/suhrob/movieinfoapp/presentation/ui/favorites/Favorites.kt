package uz.suhrob.movieinfoapp.presentation.ui.favorites

import com.arkivanov.decompose.value.Value
import uz.suhrob.movieinfoapp.domain.model.Movie

interface Favorites {

    val state: Value<FavoritesState>

    fun sendEvent(event: FavoritesEvent)

    data class FavoritesState(
        val movies: List<Movie> = listOf(),
    )
}