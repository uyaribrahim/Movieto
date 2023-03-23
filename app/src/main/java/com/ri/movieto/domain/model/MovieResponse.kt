package com.ri.movieto.domain.model

data class MovieResponse(
    val page: Int,
    var movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
) {
    data class Movie(
        val backdrop_path: String,
        val genre_ids: List<Int>,
        val id: Int,
        val overview: String,
        val poster_path: String,
        val release_date: String,
        val release_year: String,
        val title: String,
        val video: Boolean,
        val vote_average: String,
    )
}
