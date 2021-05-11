package com.ahmedtikiwa.insight.network

import com.ahmedtikiwa.insight.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Connection interceptor to ensure that the OMDb API key is included
 * in every request
 */
class OmdbConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.OMDB_API_KEY)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()

        return chain.proceed(request)
    }
}