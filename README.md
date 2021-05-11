# Insight: Movies and Series Search
Insight: Movies and TV Search is a Jetpack-powered, MVVM Kotlin Android application that retrieves movie
and series information from the OMDb API. The user is presented with a search field upon entry where
they are required to provide a title of either a movie or series. Searching can either be for a movie 
or a series via the two call-to-action buttons presented. The results are displayed below the search
field and the user can click on the result to view the details.

### Pre-requisites
Please obtain an API key from http://www.omdbapi.com/ before launching the application. You will 
need to place this key in gradle.properties as part of the OmdbKey property.

```
OmdbKey="[your API key goes here]"
```

## Architecture
The Insight: Movies and TV Search is built using Kotlin and the following Jetpack components:

- ViewModel & LiveData 
- Navigation
- Hilt

and the following additional libraries:

- Retrofit & OkHttp
- Glide
- Material Design

### Screenshots
<img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_1.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_2.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_3.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_4.png" width="400"> <img src="https://github.com/akitikkx/Insight/blob/main/screenshots/insight_screen_5.png" width="400">

### License

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
