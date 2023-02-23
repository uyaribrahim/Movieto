package com.ri.movieto.domain.mapper

import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.decider.MovieItemDecider
import com.ri.movieto.domain.model.MovieDetail
import javax.inject.Inject

class MovieDetailDtoToDomain @Inject constructor(private val decider: MovieItemDecider) :
    Mapper<MovieDetailDto, MovieDetail> {
    override fun mapFrom(input: MovieDetailDto): MovieDetail {
        return MovieDetail(
            id = input.id,
            overview = input.overview,
            title = input.title,
            poster_path = decider.providePosterPath(input.poster_path),
            vote_average = decider.provideRoundedAverage(input.vote_average),
            movie_label = decider.provideMovieLabel(input.release_date, input.genres),
        )
    }
}