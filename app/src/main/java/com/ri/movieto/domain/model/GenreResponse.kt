package com.ri.movieto.domain.model

data class GenreResponse(
    var genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}