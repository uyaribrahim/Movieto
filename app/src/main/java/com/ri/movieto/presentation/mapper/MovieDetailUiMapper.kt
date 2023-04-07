package com.ri.movieto.presentation.mapper

import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.presentation.state.MovieDetailUI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun MovieDetail.toMovieDetailUI(): MovieDetailUI{
    val roundedAverage = (vote_average * 10.0).roundToInt() / 10.0


    return MovieDetailUI(
        id = id,
        title = title,
        movie_label = movie_label,
        overview = overview,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        vote_average = roundedAverage.toString(),
        trailer_key = trailer_key,
        clip_key = clip_key,
        tagline = tagline,
        release_date = release_date,
    )
}

fun MovieDetailUI.toMovie(): MovieResponse.Movie {
    return MovieResponse.Movie(
        id = id,
        title = title,
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        vote_average = vote_average.toDouble(),
        release_date = release_date,
    )
}