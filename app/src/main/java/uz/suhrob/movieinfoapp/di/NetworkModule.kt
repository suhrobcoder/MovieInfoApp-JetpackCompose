package uz.suhrob.movieinfoapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.data.remote.AuthInterceptor
import uz.suhrob.movieinfoapp.other.BASE_URL

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    single {
        OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .client(get())
            .build()
    }
    single {
        get<Retrofit>().create(ApiService::class.java)
    }
}