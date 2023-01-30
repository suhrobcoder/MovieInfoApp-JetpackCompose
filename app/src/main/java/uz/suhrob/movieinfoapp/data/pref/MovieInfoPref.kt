package uz.suhrob.movieinfoapp.data.pref

import kotlinx.coroutines.flow.Flow

interface MovieInfoPref {
    val guestSessionId: Flow<String?>

    suspend fun setGuestSessionId(id: String)
}