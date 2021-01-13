package uz.suhrob.movieinfoapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.presentation.ui.details.DetailsViewModel
import uz.suhrob.movieinfoapp.presentation.ui.home.HomeViewModel
import uz.suhrob.movieinfoapp.presentation.ui.search.SearchViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: MovieRepository, private val id: Int? = null) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repository, id!!) as T
        } else {
            throw IllegalStateException("Unknown ViewModel class")
        }
    }
}