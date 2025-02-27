package uz.suhrob.movieinfoapp.di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.createApiService
import uz.suhrob.movieinfoapp.other.API_KEY
import uz.suhrob.movieinfoapp.other.BASE_URL

val networkModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    parameters.append("api_key", API_KEY)
                }
            }
        }
    }
    single<Ktorfit> {
        Ktorfit.Builder()
            .httpClient(get<HttpClient>())
            .build()
    }
    single<ApiService> { get<Ktorfit>().createApiService() }
}