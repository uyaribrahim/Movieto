package com.ri.movieto.data.remote.dto

import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.Recommend

data class SimilarDto(
    val page: Int,
    val results: List<MovieResponseDto.MovieDto>,
    val total_pages: Int,
    val total_results: Int
)

fun SimilarDto.toDomain(decider: MovieDecider): Recommend {
    return Recommend(
        results = results.filter { it.backdrop_path != null }
            .map { movie ->
                Recommend.Movie(
                    id = movie.id,
                    title = movie.title,
                    backdrop_path = decider.provideBackdropPath(movie.backdrop_path),
                    release_date = decider.provideReleaseYear(movie.release_date)
                )
            }
    )
}