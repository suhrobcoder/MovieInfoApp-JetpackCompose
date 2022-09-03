package uz.suhrob.movieinfoapp.data.local.entity.relations

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_genre_cross_ref", primaryKeys = ["movieId", "genreId"])
data class MovieGenreCrossRef(
    val movieId: Int,
    @ColumnInfo(index = true) val genreId: Int
)