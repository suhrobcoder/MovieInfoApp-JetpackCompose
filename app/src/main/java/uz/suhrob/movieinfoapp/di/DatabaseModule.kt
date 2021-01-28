package uz.suhrob.movieinfoapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import uz.suhrob.movieinfoapp.data.local.DATABASE_NAME
import uz.suhrob.movieinfoapp.data.local.MovieDatabase
import uz.suhrob.movieinfoapp.data.local.dao.MovieDao

@InstallIn(ActivityRetainedComponent::class)
@Module
object DatabaseModule {
    @ActivityRetainedScoped
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        DATABASE_NAME
    ).build()

    @ActivityRetainedScoped
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.getMovieDao()
}