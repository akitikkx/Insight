package com.ahmedtikiwa.insight.network.models

import com.ahmedtikiwa.insight.domain.TvMovieDetail

data class NetworkTvMovieDetailResponse(
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
    val Ratings: List<Rating>,
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

data class Rating(
    val Source: String,
    val Value: String
)

fun NetworkTvMovieDetailResponse.asDomainModel() : TvMovieDetail {
    return TvMovieDetail(
        imdbID = imdbID,
        title = Title,
        poster = Poster,
        genre = Genre,
        plot = Plot,
        language = Language,
        country = Country,
        runtime = Runtime,
        rated = Rated,
        released = Released,
        imdbRating = imdbRating
    )
}