package uz.suhrob.movieinfoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import uz.suhrob.movieinfoapp.data.local.dao.MovieDao
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepositoryImpl
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepositoryImpl

@InstallIn(ActivityRetainedComponent::class)
@Module
object ViewModelModule {
    @ActivityRetainedScoped
    @Provides
    fun provideMovieRepository(service: ApiService, pref: MovieInfoPref): MovieRepository = MovieRepositoryImpl(service, pref)

    @ActivityRetainedScoped
    @Provides
    fun provideFavoritesRepository(movieDao: MovieDao): FavoritesRepository =
        FavoritesRepositoryImpl(movieDao)
}