package com.ahmedtikiwa.insight.ui.search

import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.ahmedtikiwa.insight.domain.SearchResultArg
import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: OmdbRepository
) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _movieSearchRequest = MutableLiveData<Boolean>()
    val movieSearchRequest: LiveData<Boolean> = _movieSearchRequest

    private val _seriesSearchRequest = MutableLiveData<Boolean>()
    val seriesSearchRequest: LiveData<Boolean> = _seriesSearchRequest

    val movieSearchResult = repository.movieSearch

    val seriesSearchResult = repository.seriesSearch

    val isLoading = repository.isLoading

    val error = repository.error

    private val _selectedResult = MutableLiveData<SearchResultArg?>()
    val selectedResult: LiveData<SearchResultArg?> = _selectedResult

    fun onMovieSearchClick() {
        _movieSearchRequest.postValue(true)
    }

    fun onSeriesSearchClick() {
        _seriesSearchRequest.postValue(true)
    }

    init {
        val searchType = savedStateHandle.get<String>(SEARCH_TYPE)

        if (!searchType.isNullOrEmpty() && searchType == SEARCH_TYPE_MOVIE) {
            viewModelScope.launch {
                savedStateHandle.get<String>(SEARCH_QUERY)?.let { repository.getMovieSearch(it) }
            }
        } else if (!searchType.isNullOrEmpty() && searchType == SEARCH_TYPE_SERIES){
            viewModelScope.launch {
                savedStateHandle.get<String>(SEARCH_QUERY)?.let { repository.getSeriesSearch(it) }
            }
        }
    }

    fun onMovieQueryReceived(query: String) {
        savedStateHandle.set(SEARCH_QUERY, query)
        savedStateHandle.set(SEARCH_TYPE, SEARCH_TYPE_MOVIE)

        viewModelScope.launch {
            savedStateHandle.get<String>(SEARCH_QUERY)?.let { repository.getMovieSearch(it) }
        }
    }

    fun onSeriesQueryReceived(query: String) {
        savedStateHandle.set(SEARCH_QUERY, query)
        savedStateHandle.set(SEARCH_TYPE, SEARCH_TYPE_SERIES)

        viewModelScope.launch {
            savedStateHandle.get<String>(SEARCH_QUERY)?.let { repository.getSeriesSearch(it) }
        }
    }

    fun onSearchResultClick(result: SearchResultArg) {
        _selectedResult.postValue(result)
    }

    fun onSearchRequestCompleted() {
        _movieSearchRequest.postValue(false)
        _seriesSearchRequest.postValue(false)
    }

    fun navigateToDetailComplete() {
        _selectedResult.postValue(null)
    }

    companion object {
        const val SEARCH_QUERY = "query"
        const val SEARCH_TYPE = "search_type"
        const val SEARCH_TYPE_MOVIE = "movie"
        const val SEARCH_TYPE_SERIES = "series"
    }

    @AssistedFactory
    interface SearchViewModelFactory {
        fun create(
            owner: SavedStateRegistryOwner
        ): Factory
    }

    class Factory @AssistedInject constructor(
        @Assisted owner: SavedStateRegistryOwner,
        private val repository: OmdbRepository
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return SearchViewModel(handle, repository) as T
        }
    }

}