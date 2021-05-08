package com.ahmedtikiwa.insight.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedtikiwa.insight.domain.MovieSearch
import com.ahmedtikiwa.insight.domain.SeriesSearch
import com.ahmedtikiwa.insight.domain.SeriesMovieDetail
import com.ahmedtikiwa.insight.network.models.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OmdbRepository {

    private val _seriesSearch = MutableLiveData<SeriesSearch>()
    val seriesSearch: LiveData<SeriesSearch> = _seriesSearch

    private val _movieSearch = MutableLiveData<MovieSearch>()
    val movieSearch: LiveData<MovieSearch> = _movieSearch

    private val _detail = MutableLiveData<SeriesMovieDetail>()
    val detail: LiveData<SeriesMovieDetail> = _detail

    suspend fun getSeriesSearch(title: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = OmdbNetwork.omdbApi.getSeriesSearchAsync(title).await()
                _seriesSearch.postValue(response.asDomainModel())
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getMovieSearch(title: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = OmdbNetwork.omdbApi.getMovieSearchAsync(title).await()
                _movieSearch.postValue(response.asDomainModel())
            } catch (e: Exception) {

            }
        }
    }

    suspend fun getSeriesMovieDetail(imdbID: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = OmdbNetwork.omdbApi.getSeriesMovieDetailAsync(imdbID).await()
                _detail.postValue(response.asDomainModel())
            } catch (e: Exception) {

            }
        }
    }

}