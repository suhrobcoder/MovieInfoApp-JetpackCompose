package uz.suhrob.movieinfoapp.data.local.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity

data class MovieWithGenre(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movie_id",
        entity = GenreEntity::class,
        entityColumn = "genre_id",
        associateBy = Junction(
            value = MovieGenreCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "genreId"
        )
    )
    val genres: List<GenreEntity>
)