package uz.suhrob.movieinfoapp.di

import androidx.room.Room
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.data.local.DATABASE_NAME
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.pref.MovieInfoPref

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
    single { MovieInfoPref(get()) }
    factory {
        get<MovieDatabase>().getMovieDao()
    }
}