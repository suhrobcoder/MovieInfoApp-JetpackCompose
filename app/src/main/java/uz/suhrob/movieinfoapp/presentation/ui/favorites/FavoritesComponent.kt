package uz.suhrob.movieinfoapp.presentation.ui.favorites

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.reduce
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.model.Movie

class FavoritesComponent(
    private val repository: FavoritesRepository,
    ioScope: CoroutineScope,
    private val navigateToDetails: (Movie) -> Unit,
) : Favorites {

    private val _state = MutableValue(Favorites.FavoritesState())
    override val state: Value<Favorites.FavoritesState> = _state

    init {
        ioScope.launch {
            repository.getAllMovies().collect { movies ->
                _state.reduce { it.copy(movies = movies) }
            }
        }
    }

    override fun sendEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.MovieClick -> navigateToDetails(event.movie)
        }
    }
}