package com.ahmedtikiwa.insight.ui.search

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.savedstate.SavedStateRegistryOwner
import com.ahmedtikiwa.insight.domain.SearchItem
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

    val movieSearchResult = MediatorLiveData<PagingData<SearchItem>>()

    val seriesSearchResult = MediatorLiveData<PagingData<SearchItem>>()

    val isLoading = repository.isLoading

    val error = repository.error

    /**
     * Invoked when the user clicks on 'Search Movie' to trigger the fragment
     * to retrieve the posted title
     */
    fun onMovieSearchClick() {
        _movieSearchRequest.postValue(true)
    }

    /**
     * Invoked when the user clicks on 'Search Series' to trigger the fragment
     * to retrieve the posted title
     */
    fun onSeriesSearchClick() {
        _seriesSearchRequest.postValue(true)
    }

    init {
        // retrieving the current saved state - movie or series search
        val searchType = savedStateHandle.get<String>(SEARCH_TYPE)

        // retrieving the movie/series detail based on the saved state if not null
        if (!searchType.isNullOrEmpty() && searchType == SEARCH_TYPE_MOVIE) {
            viewModelScope.launch {
                savedStateHandle.get<String>(SEARCH_QUERY)?.let {
                    movieSearchResult.addSource(
                        repository.getMovieSearch(it).cachedIn(this)
                    ) { result ->
                        movieSearchResult.value = result
                    }
                }
            }
        } else if (!searchType.isNullOrEmpty() && searchType == SEARCH_TYPE_SERIES) {
            viewModelScope.launch {
                savedStateHandle.get<String>(SEARCH_QUERY)?.let {
                    seriesSearchResult.addSource(
                        repository.getSeriesSearch(it).cachedIn(this)
                    ) { result ->
                        seriesSearchResult.value = result
                    }
                }
            }
        }
    }

    /**
     * Invoked when the user clicks on the 'Search Movie' call-to-action and the text from the
     * search field has been retrieved to be passed on to the repository
     */
    fun onMovieQueryReceived(query: String) {
        // save the current state using the current query and movie search type as reference
        savedStateHandle.set(SEARCH_QUERY, query)
        savedStateHandle.set(SEARCH_TYPE, SEARCH_TYPE_MOVIE)

        // Query the repository for the requested movie title
        viewModelScope.launch {
            savedStateHandle.get<String>(SEARCH_QUERY)?.let {
                movieSearchResult.addSource(
                    repository.getMovieSearch(it).cachedIn(this)
                ) { result ->
                    movieSearchResult.value = result
                }
            }
        }
    }

    /**
     * Invoked when the user clicks on the 'Search Series' call-to-action and the text from the
     * search field has been retrieved to be passed on to the repository
     */
    fun onSeriesQueryReceived(query: String) {
        // save the current state using the current query and series search type as reference
        savedStateHandle.set(SEARCH_QUERY, query)
        savedStateHandle.set(SEARCH_TYPE, SEARCH_TYPE_SERIES)

        // Query the repository for the requested series title
        viewModelScope.launch {
            savedStateHandle.get<String>(SEARCH_QUERY)?.let {
                seriesSearchResult.addSource(
                    repository.getSeriesSearch(it).cachedIn(this)
                ) { result ->
                    seriesSearchResult.value = result
                }
            }
        }
    }

    /**
     * Clearing the current requests once search has completed
     */
    fun onSearchRequestCompleted() {
        _movieSearchRequest.postValue(false)
        _seriesSearchRequest.postValue(false)
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