package com.ri.movieto.presentation.mapper

import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.presentation.state.MovieUIItem

fun MovieResponse.Movie.toMovieUIItem(): MovieUIItem {
    val decider = MovieDecider()

    val backdropPath = if (backdrop_path.isNotEmpty()) {
        decider.provideBackdropPath(backdrop_path)
    } else {
        ""
    }
    val posterPath = if (poster_path.isNotEmpty()) {
        decider.providePosterPath(poster_path)
    } else {
        ""
    }

    return MovieUIItem(
        id = id,
        title = title,
        vote_average = decider.provideRoundedAverage(vote_average),
        backdrop_path = backdropPath,
        poster_path = posterPath,
        release_year = decider.provideReleaseYear(release_date)
    )

}