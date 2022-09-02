package uz.suhrob.movieinfoapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import uz.suhrob.movieinfoapp.other.API_KEY

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val url = originalUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}