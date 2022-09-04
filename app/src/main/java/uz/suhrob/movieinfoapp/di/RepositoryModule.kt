package uz.suhrob.movieinfoapp.di

import org.koin.dsl.module
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepositoryImpl
import uz.suhrob.movieinfoapp.data.repository.MovieRepository
import uz.suhrob.movieinfoapp.data.repository.MovieRepositoryImpl

val repositoryModule = module {
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    factory<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
}