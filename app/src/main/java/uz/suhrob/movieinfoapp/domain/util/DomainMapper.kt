package uz.suhrob.movieinfoapp.domain.util

abstract class DomainMapper<T, DomainModel> {
    abstract fun mapToDomainModel(model: T): DomainModel

    abstract fun mapFromDomainModel(domainModel: DomainModel): T

    fun mapToList(models: List<T>): List<DomainModel> {
        return models.map { mapToDomainModel(it) }
    }

    fun mapFromList(models: List<DomainModel>): List<T> {
        return models.map { mapFromDomainModel(it) }
    }
}