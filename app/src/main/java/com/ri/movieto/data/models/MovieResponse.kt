package com.ri.movieto.data.models

import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_results: Int,
    val total_pages: Int
) {
    data class Movie(
        val id: Int,
        val title: String,
        val poster_path: String?,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val overview: String,
        val vote_average: Float,
        val release_date: String
    ) : Serializable
}
