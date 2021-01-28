package uz.suhrob.movieinfoapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ActivityRetainedScoped
class MovieInfoPref @Inject constructor(@ApplicationContext context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "${context.packageName}.movie_info_pref"
    )

    val guestSessionId: Flow<String?>
        get() = dataStore.data.map { prefs -> prefs[KEY_GUEST_SESSION_ID] }

    suspend fun setGuestSessionId(id: String) {
        dataStore.edit { prefs ->
            prefs[KEY_GUEST_SESSION_ID] = id
        }
    }

    companion object {
        private val KEY_GUEST_SESSION_ID = preferencesKey<String>("guest_session_id_key")
    }
}