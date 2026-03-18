package uz.suhrob.movieinfoapp.presentation.ui.favorites

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.presentation.base.BaseViewModel

class FavoritesViewModel(
    private val repository: FavoritesRepository,
) : BaseViewModel<FavoritesState, FavoritesEvent, FavoritesEffect>(FavoritesState()) {

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect { movies ->
                setState { copy(movies = movies) }
            }
        }
    }

    override fun handleEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.MovieClick -> sendEffect(FavoritesEffect.NavigateToDetails(event.movie))
        }
    }
}
