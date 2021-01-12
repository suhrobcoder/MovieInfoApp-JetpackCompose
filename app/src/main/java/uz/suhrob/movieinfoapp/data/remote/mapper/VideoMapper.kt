package uz.suhrob.movieinfoapp.data.remote.mapper

import uz.suhrob.movieinfoapp.data.remote.model.MovieVideoDto
import uz.suhrob.movieinfoapp.domain.model.Video
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object VideoMapper : DomainMapper<MovieVideoDto, Video>() {
    override fun mapToDomainModel(model: MovieVideoDto): Video {
        return Video(
            id = model.id ?: "",
            key = model.key ?: "",
            name = model.name ?: "",
            site = model.site ?: "",
            type = model.type ?: ""
        )
    }

    override fun mapFromDomainModel(domainModel: Video): MovieVideoDto {
        return MovieVideoDto(
            id = domainModel.id,
            key = domainModel.key,
            name = domainModel.name,
            site = domainModel.site,
            type = domainModel.type
        )
    }
}