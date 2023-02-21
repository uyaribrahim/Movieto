package com.ri.movieto.domain.mapper

import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.domain.model.GenreResponse

class GenreResponseDtoToDomain : Mapper<GenreResponseDto, GenreResponse> {
    override fun mapFrom(input: GenreResponseDto): GenreResponse {
        return GenreResponse(
            genres = input.genres.map { genre ->
                GenreResponse.Genre(
                    id = genre.id,
                    name = genre.name
                )
            }
        )
    }
}