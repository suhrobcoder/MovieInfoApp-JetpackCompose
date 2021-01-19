package uz.suhrob.movieinfoapp.data.remote.mapper

import uz.suhrob.movieinfoapp.data.remote.model.MovieDto
import uz.suhrob.movieinfoapp.domain.model.Movie
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object MovieMapper : DomainMapper<MovieDto, Movie>() {
    override fun mapToDomainModel(model: MovieDto): Movie {
        return Movie(
            id = model.id,
            title = model.title ?: "",
            overview = model.overview ?: "",
            backdropPath = model.backdrop_path ?: "",
            posterPath = model.poster_path ?: "",
            genres = GenreMapper.mapToList(model.genres),
            releaseDate = model.release_date,
            runtime = model.runtime,
            video = model.video ?: false,
            voteCount = model.vote_count ?: -1,
            voteAverage = model.vote_average ?: -1.0
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieDto {
        return MovieDto(
            id = domainModel.id,
            title = domainModel.title,
            overview = domainModel.overview,
            backdrop_path = domainModel.backdropPath,
            poster_path = domainModel.posterPath,
            genres = GenreMapper.mapFromList(domainModel.genres),
            release_date = domainModel.releaseDate,
            video = domainModel.video,
            vote_count = domainModel.voteCount,
            vote_average = domainModel.voteAverage
        )
    }
}