package uz.suhrob.movieinfoapp.data.remote.mapper

import uz.suhrob.movieinfoapp.data.remote.model.MovieReviewDto
import uz.suhrob.movieinfoapp.data.remote.model.ReviewAuthor
import uz.suhrob.movieinfoapp.domain.model.Review
import uz.suhrob.movieinfoapp.domain.util.DomainMapper

object ReviewMapper : DomainMapper<MovieReviewDto, Review>() {
    override fun mapToDomainModel(model: MovieReviewDto): Review {
        return Review(
            id = model.id ?: "",
            content = model.content ?: "",
            createdAt = model.createdAt ?: "",
            authorName = model.authorDetails.name ?: "",
            avatarPath = model.authorDetails.avatarPath ?: ""
        )
    }

    override fun mapFromDomainModel(domainModel: Review): MovieReviewDto {
        return MovieReviewDto(
            id = domainModel.id,
            content = domainModel.content,
            createdAt = domainModel.createdAt,
            author = domainModel.authorName,
            authorDetails = ReviewAuthor(
                name = domainModel.authorName,
                avatarPath = domainModel.avatarPath
            )
        )
    }
}