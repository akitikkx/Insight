package com.ahmedtikiwa.insight.network.models

import com.ahmedtikiwa.insight.domain.SearchItem

data class NetworkSearchResponse(
    val Response: String?,
    val Search: List<NetworkSearchResponseItem>?,
    val totalResults: String?
)

data class NetworkSearchResponseItem(
    val Poster: String?,
    val Title: String?,
    val Type: String?,
    val Year: String?,
    val imdbID: String?
)

fun List<NetworkSearchResponseItem>?.asDomainModel(): List<SearchItem>? {
    return this?.map {
        SearchItem(
            imdbID = it.imdbID,
            title = it.Title,
            year = it.Year,
            poster = it.Poster
        )
    }
}