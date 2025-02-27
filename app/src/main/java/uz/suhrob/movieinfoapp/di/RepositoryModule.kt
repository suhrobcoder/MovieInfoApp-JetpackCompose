package uz.suhrob.movieinfoapp.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.data.repository.FavoritesRepositoryImpl
import uz.suhrob.movieinfoapp.data.repository.MovieRepositoryImpl
import uz.suhrob.movieinfoapp.domain.repository.FavoritesRepository
import uz.suhrob.movieinfoapp.domain.repository.MovieRepository

val repositoryModule = module {
    singleOf(::FavoritesRepositoryImpl) { bind<FavoritesRepository>() }
    singleOf(::MovieRepositoryImpl) { bind<MovieRepository>() }
}