package com.ahmedtikiwa.insight.network.models

import com.ahmedtikiwa.insight.domain.MovieSearch

data class NetworkMovieSearchResponse(
    val Actors: String,
    val Awards: String,
    val BoxOffice: String,
    val Country: String,
    val DVD: String,
    val Director: String,
    val Error: String?,
    val Genre: String,
    val Language: String,
    val Metascore: String,
    val Plot: String,
    val Poster: String,
    val Production: String,
    val Rated: String,
    val Ratings: List<NetworkMovieSearchResponseRating>,
    val Released: String,
    val Response: String,
    val Runtime: String,
    val Title: String,
    val Type: String,
    val Website: String,
    val Writer: String,
    val Year: String,
    val imdbID: String,
    val imdbRating: String,
    val imdbVotes: String
)

data class NetworkMovieSearchResponseRating(
    val Source: String,
    val Value: String
)

fun NetworkMovieSearchResponse.asDomainModel() : MovieSearch {
    return MovieSearch(
        imdbID = imdbID,
        title = Title,
        year = Year,
        poster = Poster,
        response = Response.toBoolean(),
        error = Error
    )
}