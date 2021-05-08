package com.ahmedtikiwa.insight.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val repository: OmdbRepository
) : AndroidViewModel(application) {

    private val _movieSearchRequest = MutableLiveData<Boolean>()
    val movieSearchRequest: LiveData<Boolean> = _movieSearchRequest

    private val _seriesSearchRequest = MutableLiveData<Boolean>()
    val seriesSearchRequest: LiveData<Boolean> = _seriesSearchRequest

    fun onMovieSearchClick() {
        _movieSearchRequest.postValue(true)
    }

    fun onSeriesSearchClick() {
        _seriesSearchRequest.postValue(true)
    }

    fun onMovieQueryReceived(query: String) {

    }

    fun onSeriesQueryReceived(query: String) {

    }

}