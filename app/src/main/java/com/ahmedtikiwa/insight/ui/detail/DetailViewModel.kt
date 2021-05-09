package com.ahmedtikiwa.insight.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmedtikiwa.insight.repository.OmdbRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DetailViewModel @AssistedInject constructor(
    application: Application,
    repository: OmdbRepository,
    @Assisted imdbID: String
) : AndroidViewModel(application) {

    @AssistedFactory
    interface DetailViewModelFactory {
        fun create(imdbID: String): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: DetailViewModelFactory,
            imdbID: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(imdbID) as T
            }
        }
    }
}