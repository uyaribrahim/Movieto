package com.ri.movieto.domain.decider

import com.ri.movieto.data.remote.dto.movie_detail.Genre

interface MovieItemDecider {
    fun provideBackdropPath(path: String?): String

    fun providePosterPath(path: String?): String

    fun provideRoundedAverage(vote_average: Double): String

    fun provideReleaseYear(date: String): String

    fun provideMovieLabel(date: String, genres: List<Genre>): String
}