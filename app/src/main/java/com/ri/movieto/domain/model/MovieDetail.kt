package com.ri.movieto.domain.model

data class MovieDetail(
    val id: Int,
    val movie_label: String,
    val overview: String?,
    val title: String,
    val poster_path: String?,
    val vote_average: String,
    val trailer_key: String = ""
)
