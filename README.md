# Insight: Movies and Series Search
`Insight: Movies and TV Search` is a Jetpack-powered, MVVM `Kotlin` Android application that retrieves movie
and series information from the OMDb API. The user is presented with a search field upon entry where
they are required to provide a title of either a movie or series. Searching can either be for a movie 
or a series via the two call-to-action buttons presented. The results are displayed below the search
field and the user can click on the result to view the details.

### Pre-requisites
Please obtain an API key from http://www.omdbapi.com/ before launching the application. You will 
need to place this key in gradle.properties as part of the `OMDbKey` property.

```
OmdbKey="[your API key goes here]"
```

## Architecture
`The Insight: Movies and TV Search` is built using Kotlin and the following Jetpack components:

- ViewModel & LiveData 
- Navigation
- SavedStateHandle with custom AbstractSavedStateViewModelFactory
- View Binding
- Data Binding
- Paging 3

and the following additional libraries:

- Hilt
- Retrofit & OkHttp
- Glide
- Material Design

## Code and directory structure

```
> adapters
  |_ GlideBindingAdapter.kt
  |_ ResultBindingAdapter.kt
  |_ SearchBindingAdapter.kt
> di
  |_ RepositoryModule.kt
> domain
  |_ MovieSearch.kt
  |_ SearchItem.kt
  |_ SearchType.kt
  |_ SeriesMovieDetail.kt
> network
  > models
    |_ NetworkSearchResponse.kt
    |_ NetworkSeriesMovieDetailResponse.kt
  |_ OmdbConnectionInterceptor.kt
  |_ OmdbService.kt
> repository
  |_ OmdbPagingSource.kt
  |_ OmdbRepository.kt
> ui
  > detail
    |_ DetailFragment.kt
    |_ DetailViewModel.kt
  > search
    |_ SearchFragment.kt
    |_ SearchResultsAdapter.kt
    |_ SearchResultsLoadStateAdapter.kt
    |_ SearchViewModel.kt
|_ InsightApplication.kt
|_ MainActivity.kt
```

#### adapters
All the binding adapters for the search and result screen/section as well as for loading images via Glide

#### di
All the related Dependency Injection classes such as modules for the Hilt component

#### domain
Contained here are data classes whose attributes match the preference of the application as compared to 
what is received from the network. For example, the `Response` attribute in the response from OMDb is 
not a `Boolean` but a `String`, so the attribute contained in the data object from the network that is a `String`
is converted to `Boolean` to perform `Boolean` related functions in the app. 

#### network
The OMDb connection service and request interceptor

#### network > models
The data classes modelled against the responses from OMDb

#### repository
All data to the app originates from the repository. For `Insight: Movies and Series Search`, the repository
performs the network call through the connection service and emits the data which the viewModels listen for. This
repository makes use of the Jetpack Paging 3 library to load the data from the network in a paged way.

#### ui
All the screens in the app separated into - search and detail - and their respective fragments and viewModels

#### InsightApplication
`@HiltAndroidApp` annotated Application class

#### MainActivity
The entry point for the app and the container for the `Navigation`'s `NavHostFragment`

## Screenshots
<img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_1.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_2.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_3.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_4.png" width="400">

## License

MIT License

Copyright (c) 2021 Ahmed Tikiwa

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
