package com.ahmedtikiwa.insight.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmedtikiwa.insight.domain.SearchResultArg
import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    application: Application,
    private val repository: OmdbRepository
) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _movieSearchRequest = MutableLiveData<Boolean>()
    val movieSearchRequest: LiveData<Boolean> = _movieSearchRequest

    private val _seriesSearchRequest = MutableLiveData<Boolean>()
    val seriesSearchRequest: LiveData<Boolean> = _seriesSearchRequest

    val movieSearchResult = repository.movieSearch

    val seriesSearchResult = repository.seriesSearch

    private val _selectedResult = MutableLiveData<SearchResultArg>()
    val selectedResult: LiveData<SearchResultArg> = _selectedResult

    fun onMovieSearchClick() {
        _movieSearchRequest.postValue(true)
    }

    fun onSeriesSearchClick() {
        _seriesSearchRequest.postValue(true)
    }

    fun onMovieQueryReceived(query: String) {
        viewModelScope.launch {
            repository.getMovieSearch(query)
        }
    }

    fun onSeriesQueryReceived(query: String) {
        viewModelScope.launch {
            repository.getSeriesSearch(query)
        }
    }

    fun onSearchResultClick(result: SearchResultArg) {
        _selectedResult.postValue(result)
    }

}