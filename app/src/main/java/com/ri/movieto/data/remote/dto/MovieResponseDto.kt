package com.ri.movieto.data.remote.dto

import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieResponse

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
) {
    data class MovieDto(
        val adult: Boolean,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String?,
        val release_date: String?,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}

fun MovieResponseDto.toDomain(decider: MovieDecider): MovieResponse {

    return MovieResponse(
        page = page,
        movies = results.map { movie ->
            MovieResponse.Movie(
                release_year = decider.provideReleaseYear(movie.release_date),
                poster_path = decider.providePosterPath(movie.poster_path),
                backdrop_path = decider.provideBackdropPath(movie.backdrop_path),
                vote_average = decider.provideRoundedAverage(movie.vote_average),
                title = movie.title,
                id = movie.id,
                video = movie.video,
                release_date = movie.release_date ?: "",
                overview = movie.overview,
                genre_ids = movie.genre_ids
            )
        },
        total_pages = total_pages,
        total_results = total_results
    )
}
