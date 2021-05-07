package com.ahmedtikiwa.insight.network.models

import com.ahmedtikiwa.insight.domain.SeriesSearch

data class NetworkSeriesSearchReponse(
    val Actors: String,
    val Awards: String,
    val Country: String,
    val Director: String,
    val Genre: String,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Rated: String,
    val Ratings: List<NetworkSeriesSearchResponseRating>,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String,
    val totalSeasons: String
)

data class NetworkSeriesSearchResponseRating(
    val Source: String,
    val Value: String
)

fun NetworkSeriesSearchReponse.asDomainModel() : SeriesSearch {
    return SeriesSearch(
        imdbID = imdbID,
        title = Title,
        year = Year,
        poster = Poster
    )
}