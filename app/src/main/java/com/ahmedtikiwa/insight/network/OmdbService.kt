package com.ahmedtikiwa.insight.repository

import com.ahmedtikiwa.insight.network.models.NetworkMovieSearchResponse
import com.ahmedtikiwa.insight.network.models.NetworkSeriesSearchReponse
import com.ahmedtikiwa.insight.network.OmdbConnectionInterceptor
import com.ahmedtikiwa.insight.network.models.NetworkTvMovieDetailResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface OmdbService {

    @GET("{t}")
    fun getSeriesSearchAsync(
        @Path("t") t: String,
    ): Deferred<NetworkSeriesSearchReponse>

    @GET("{t}")
    fun getMovieSearchAsync(
        @Path("t") t: String,
    ): Deferred<NetworkMovieSearchResponse>

    @GET("{i}")
    fun getTvMovieDetailAsync(
        @Path("i") i: String
    ): Deferred<NetworkTvMovieDetailResponse>
}

object OmdbNetwork {
    private const val BASE_URL = "http://www.omdbapi.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(OmdbConnectionInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        .build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val omdbApi: OmdbService = retrofit.create(OmdbService::class.java)
}