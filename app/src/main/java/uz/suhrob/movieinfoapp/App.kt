package uz.suhrob.movieinfoapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import uz.suhrob.movieinfoapp.di.appModule
import uz.suhrob.movieinfoapp.di.componentModule
import uz.suhrob.movieinfoapp.di.networkModule
import uz.suhrob.movieinfoapp.di.repositoryModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule, repositoryModule, networkModule, componentModule)
        }
    }
}