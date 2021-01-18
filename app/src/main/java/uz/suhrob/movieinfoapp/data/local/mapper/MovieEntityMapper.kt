package uz.suhrob.movieinfoapp.data.local.mapper

import uz.suhrob.movieinfoapp.data.local.entity.MovieEntity
import uz.suhrob.movieinfoapp.data.local.entity.relations.MovieWithGenre
import uz.suhrob.movieinfoapp.data.remote.mapper.GenreMapper
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object MovieEntityMapper : DomainMapper<MovieWithGenre, Movie>() {
    override fun mapToDomainModel(model: MovieWithGenre): Movie {
        return Movie(
            id = model.movie.id,
            title = model.movie.title,
            overview = model.movie.overview,
            backdropPath = model.movie.backdropPath,
            posterPath = model.movie.posterPath,
            genres = GenreEntityMapper.mapToList(model.genres),
            releaseDate = model.movie.releaseDate,
            video = model.movie.video,
            voteCount = model.movie.voteCount,
            voteAverage = model.movie.voteAverage
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieWithGenre {
        return MovieWithGenre(
            movie = MovieEntity(
                id = domainModel.id,
                title = domainModel.title,
                overview = domainModel.overview,
                backdropPath = domainModel.backdropPath,
                posterPath = domainModel.posterPath,
                releaseDate = domainModel.releaseDate,
                video = domainModel.video,
                voteCount = domainModel.voteCount,
                voteAverage = domainModel.voteAverage
            ),
            genres = GenreEntityMapper.mapFromList(domainModel.genres)
        )
    }
}