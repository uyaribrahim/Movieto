package com.ri.movieto.data.remote.dto

data class GenreResponseDto (
    val genres: List<GenreDto>
) {
    data class GenreDto(
        val id: Int,
        val name: String
    )
}