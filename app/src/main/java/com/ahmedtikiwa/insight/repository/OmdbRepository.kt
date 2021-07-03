package com.ahmedtikiwa.insight.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ahmedtikiwa.insight.domain.SearchType
import com.ahmedtikiwa.insight.domain.SeriesMovieDetail
import com.ahmedtikiwa.insight.network.OmdbNetwork
import com.ahmedtikiwa.insight.network.models.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OmdbRepository {

    private val _detail = MutableLiveData<SeriesMovieDetail>()
    val detail: LiveData<SeriesMovieDetail> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    /**
     * Query the OMDb API for the requested series title
     */
    fun getSeriesSearch(title: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                OmdbPagingSource(
                    OmdbNetwork.omdbApi,
                    title,
                    SearchType.SERIES
                )
            }
        ).liveData

    /**
     * Query the OMDb API for the requested movie title
     */
    fun getMovieSearch(title: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                OmdbPagingSource(
                    OmdbNetwork.omdbApi,
                    title,
                    SearchType.MOVIE
                )
            }
        ).liveData

    /**
     * Get the movie/series information from the provided IMDb ID
     */
    suspend fun getSeriesMovieDetail(imdbID: String) {
        withContext(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)
                val response = OmdbNetwork.omdbApi.getSeriesMovieDetailAsync(imdbID).await()
                _detail.postValue(response.asDomainModel())
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message)
                _isLoading.postValue(false)
            }
        }
    }

}