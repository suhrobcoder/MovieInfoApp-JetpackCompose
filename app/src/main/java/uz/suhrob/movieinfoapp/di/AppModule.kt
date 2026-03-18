package uz.suhrob.movieinfoapp.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.data.local.DATABASE_NAME
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.pref.MovieInfoDataStore
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref

val appModule = module {
    single<MovieDatabase> {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    factory { get<MovieDatabase>().getMovieDao() }
    singleOf(::MovieInfoDataStore) { bind<MovieInfoPref>() }
}