package uz.suhrob.movieinfoapp.data.remote.mapper

import uz.suhrob.movieinfoapp.data.remote.model.GenreDto
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object GenreMapper : DomainMapper<GenreDto, Genre>() {
    override fun mapToDomainModel(model: GenreDto): Genre {
        return Genre(
            id = model.id,
            name = model.name
        )
    }

    override fun mapFromDomainModel(domainModel: Genre): GenreDto {
        return GenreDto(
            id = domainModel.id,
            name = domainModel.name
        )
    }
}