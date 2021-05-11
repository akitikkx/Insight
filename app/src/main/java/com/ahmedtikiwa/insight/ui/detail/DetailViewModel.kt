package com.ahmedtikiwa.insight.ui.detail

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: OmdbRepository,
    imdbID: String
) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val detail = repository.detail

    val isLoading = repository.isLoading

    init {
        // save the current state using the IMDb ID as a reference
        savedStateHandle.set(IMDB_ID, imdbID)

        // Get the movie/series information from the repository
        viewModelScope.launch {
            savedStateHandle.get<String>(IMDB_ID)?.let { repository.getSeriesMovieDetail(it) }
        }
    }

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(
            owner: SavedStateRegistryOwner,
            imdbID: String
        ): Factory
    }

    companion object {
        const val IMDB_ID = "imdbId"
    }

    class Factory @AssistedInject constructor(
        @Assisted owner: SavedStateRegistryOwner,
        @Assisted private val imdbID: String,
        private val repository: OmdbRepository
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return DetailViewModel(handle, repository, imdbID) as T
        }
    }
}