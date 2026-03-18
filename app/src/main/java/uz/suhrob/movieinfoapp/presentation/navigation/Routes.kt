package uz.suhrob.movieinfoapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class DetailsRoute(val movieId: Int)
