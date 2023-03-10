package com.ri.movieto.domain.model

data class Recommend (
    val results: List<Movie>,
){
    data class Movie(
        val id: Int,
        val title: String,
        val backdrop_path: String?,
        val release_date: String,
    ) : java.io.Serializable
}