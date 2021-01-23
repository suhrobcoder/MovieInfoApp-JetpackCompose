package uz.suhrob.movieinfoapp.data.remote.mapper

import uz.suhrob.movieinfoapp.data.remote.model.CastDto
import uz.suhrob.movieinfoapp.domain.model.Cast
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object CastDtoMapper : DomainMapper<CastDto, Cast>() {
    override fun mapToDomainModel(model: CastDto): Cast {
        return Cast(
            id = model.id,
            name = model.name,
            profilePath = model.profilePath,
            character = model.character
        )
    }

    override fun mapFromDomainModel(domainModel: Cast): CastDto {
        return CastDto(
            adult = false,
            gender = -1,
            id = domainModel.id,
            name = domainModel.name,
            profilePath = domainModel.profilePath,
            popularity = 0.0,
            character = domainModel.character,
            creditId = "",
            castId = 0,
            order = 0
        )
    }
}