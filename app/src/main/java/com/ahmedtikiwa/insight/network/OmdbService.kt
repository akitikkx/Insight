package com.ahmedtikiwa.insight.network

import com.ahmedtikiwa.insight.network.models.NetworkSearchResponse
import com.ahmedtikiwa.insight.network.models.NetworkSeriesMovieDetailResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface OmdbService {

    @GET("/")
    fun getSeriesSearchAsync(
        @Query("s") s: String,
        @Query("type") type: String = "series",
        @Query("page") page: Int
    ): Deferred<NetworkSearchResponse>

    @GET("/")
    fun getMovieSearchAsync(
        @Query("s") s: String,
        @Query("type") type: String = "movie",
        @Query("page") page: Int
    ): Deferred<NetworkSearchResponse>

    @GET("/")
    fun getSeriesMovieDetailAsync(
        @Query("i") i: String
    ): Deferred<NetworkSeriesMovieDetailResponse>
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