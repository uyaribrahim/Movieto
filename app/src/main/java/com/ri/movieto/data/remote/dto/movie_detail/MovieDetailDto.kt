package com.ri.movieto.data.remote.dto.movie_detail

import com.ri.movieto.data.remote.dto.movie_video.VideoResponseDto
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieDetail

data class MovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val videos: VideoResponseDto
)

fun MovieDetailDto.toDomain(decider: MovieDecider): MovieDetail {
    return MovieDetail(
        id = id,
        overview = overview,
        title = title,
        poster_path = decider.providePosterPath(poster_path),
        vote_average = decider.provideRoundedAverage(vote_average),
        movie_label = decider.provideMovieLabel(release_date, genres),
        trailer_key = decider.provideTrailerKey(videos)
    )
}