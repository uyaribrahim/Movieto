package com.ri.movieto.data.decider

import com.ri.movieto.domain.decider.MovieItemDecider
import kotlin.math.roundToInt

class MovieItemDeciderImpl : MovieItemDecider {

    override fun provideBackdropPath(path: String?): String =
        "https://www.themoviedb.org/t/p/w533_and_h300_bestv2$path"

    override fun providePosterPath(path: String?): String =
        "https://www.themoviedb.org/t/p/w600_and_h900_bestv2$path"

    override fun provideRoundedAverage(vote_average: Double): String {
        val roundOff = (vote_average * 10.0).roundToInt() / 10.0
        return roundOff.toString()
    }

    override fun provideReleaseYear(date: String): String {
        val releaseDateArray = date.split("-")
        return releaseDateArray[0]
    }
}