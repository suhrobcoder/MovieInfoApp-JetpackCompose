package uz.suhrob.movieinfoapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "movie_info_pref")

class MovieInfoDataStore constructor(
    private val context: Context,
): MovieInfoPref {

    override val guestSessionId: Flow<String?>
        get() = context.dataStore.data.map { prefs -> prefs[KEY_GUEST_SESSION_ID] }

    override suspend fun setGuestSessionId(id: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_GUEST_SESSION_ID] = id
        }
    }

    companion object {
        private val KEY_GUEST_SESSION_ID = stringPreferencesKey("guest_session_id_key")
    }
}