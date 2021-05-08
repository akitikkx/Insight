package com.ahmedtikiwa.insight.ui.searchResult

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application)