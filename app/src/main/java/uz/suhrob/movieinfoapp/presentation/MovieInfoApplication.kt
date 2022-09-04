package uz.suhrob.movieinfoapp.presentation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.suhrob.movieinfoapp.di.databaseModule
import uz.suhrob.movieinfoapp.di.networkModule
import uz.suhrob.movieinfoapp.di.repositoryModule
import uz.suhrob.movieinfoapp.di.screenModelModule

class MovieInfoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieInfoApplication)
            modules(databaseModule, networkModule, repositoryModule, screenModelModule)
        }
    }
}