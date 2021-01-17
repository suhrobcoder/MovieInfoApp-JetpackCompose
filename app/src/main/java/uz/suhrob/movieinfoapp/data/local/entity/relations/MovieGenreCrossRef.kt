package uz.suhrob.movieinfoapp.data.local.entity.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_genre_cross_ref", primaryKeys = ["movie_id", "genre_id"])
data class MovieGenreCrossRef(
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "genre_id") val genreId: Int
)