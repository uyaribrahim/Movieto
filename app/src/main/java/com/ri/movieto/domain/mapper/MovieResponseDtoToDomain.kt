package com.ri.movieto.domain.mapper

import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.domain.decider.MovieItemDecider
import com.ri.movieto.domain.model.MovieResponse
import javax.inject.Inject

class MovieResponseDtoToDomain @Inject constructor(private val itemDecider: MovieItemDecider) :
    Mapper<MovieResponseDto, MovieResponse> {
    override fun mapFrom(input: MovieResponseDto): MovieResponse {
        return MovieResponse(
            page = input.page,
            movies = input.results.map { movie ->
                MovieResponse.Movie(
                    release_year = itemDecider.provideReleaseYear(movie.release_date),
                    poster_path = itemDecider.providePosterPath(movie.poster_path),
                    backdrop_path = itemDecider.provideBackdropPath(movie.backdrop_path),
                    vote_average = itemDecider.provideRoundedAverage(movie.vote_average),
                    title = movie.title,
                    id = movie.id,
                    video = movie.video,
                    release_date = movie.release_date,
                    overview = movie.overview,
                    genre_ids = movie.genre_ids
                )
            },
            total_pages = input.total_pages,
            total_results = input.total_results
        )
    }

}