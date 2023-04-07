package com.ri.movieto.domain.model

data class MovieResponse(
    val page: Int,
    var movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Movie(
        val backdrop_path: String,
        val id: Int,
        val poster_path: String,
        val release_date: Long,
        val title: String,
        val vote_average: Double,
    )
}
