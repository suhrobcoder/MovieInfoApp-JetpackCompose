package uz.suhrob.movieinfoapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "genre_id") val id: Int,
    @ColumnInfo(name = "name") val name: String
)