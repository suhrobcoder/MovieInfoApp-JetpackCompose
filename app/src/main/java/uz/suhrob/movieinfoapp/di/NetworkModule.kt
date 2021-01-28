package uz.suhrob.movieinfoapp.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import uz.suhrob.movieinfoapp.data.remote.ApiService
import uz.suhrob.movieinfoapp.other.BASE_URL

@InstallIn(ActivityRetainedComponent::class)
@Module
object NetworkModule {

    @ActivityRetainedScoped
    @Provides
    @ExperimentalSerializationApi
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory(MediaType.get("application/json"))
        )
        .build()

    @ActivityRetainedScoped
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}