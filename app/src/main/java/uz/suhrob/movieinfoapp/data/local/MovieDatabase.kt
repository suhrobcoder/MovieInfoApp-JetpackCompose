package uz.suhrob.movieinfoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.suhrob.movieinfoapp.data.local.dao.MovieDao
import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieGenreCrossRef

private const val DATABASE_VERSION = 1
const val DATABASE_NAME = "movie.db"

@Database(
    entities = [MovieEntity::class, GenreEntity::class, MovieGenreCrossRef::class],
    version = DATABASE_VERSION
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            if (instance == null) {
                synchronized(MovieDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return instance!!
        }
    }
}