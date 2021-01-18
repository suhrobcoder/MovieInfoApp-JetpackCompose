package uz.suhrob.movieinfoapp.data.local.mapper

import uz.suhrob.movieinfoapp.data.local.entity.GenreEntity
import uz.suhrob.movieinfoapp.domain.model.Genre
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object GenreEntityMapper : DomainMapper<GenreEntity, Genre>() {
    override fun mapToDomainModel(model: GenreEntity): Genre {
        return Genre(
            id = model.id,
            name = model.name
        )
    }

    override fun mapFromDomainModel(domainModel: Genre): GenreEntity {
        return GenreEntity(
            id = domainModel.id,
            name = domainModel.name
        )
    }
}