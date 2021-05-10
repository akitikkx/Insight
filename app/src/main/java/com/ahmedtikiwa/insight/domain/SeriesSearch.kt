package com.ahmedtikiwa.insight.domain

data class SeriesSearch(
    val poster: String?,
    val title: String?,
    val year: String?,
    val imdbID: String?,
    val response: Boolean?,
    val error: String?
)