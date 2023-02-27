package com.ri.movieto.data.remote.dto

import com.ri.movieto.domain.model.GenreResponse

data class GenreResponseDto(
    val genres: List<GenreDto>
) {
    data class GenreDto(
        val id: Int,
        val name: String
    )
}

fun GenreResponseDto.toDomain(): GenreResponse {
    return GenreResponse(
        genres = genres.map { genre ->
            GenreResponse.Genre(
                id = genre.id,
                name = genre.name
            )
        }
    )
}