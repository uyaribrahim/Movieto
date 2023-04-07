package com.ri.movieto.presentation.mapper

import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.presentation.state.MovieUIItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

fun MovieResponse.Movie.toMovieUIItem(): MovieUIItem {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = Date(release_date)
    val c = Calendar.getInstance()
    c.timeInMillis = release_date
    val year = c.get(Calendar.YEAR).toString()

    val roundedAverage = (vote_average * 10.0).roundToInt() / 10.0

    return MovieUIItem(
        id = id,
        title = title,
        vote_average = roundedAverage.toString(),
        backdrop_path = backdrop_path,
        poster_path = poster_path,
        release_date = inputSDF.format(date),
        release_year = year
    )
}

fun MovieUIItem.toMovie(): MovieResponse.Movie {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputSDF.parse(release_date)
    val milliseconds = date?.time ?: 0

    return MovieResponse.Movie(
        id = id,
        title = title,
        vote_average = vote_average.toDouble(),
        backdrop_path = backdrop_path,
        poster_path = poster_path,
        release_date = milliseconds
    )
}
