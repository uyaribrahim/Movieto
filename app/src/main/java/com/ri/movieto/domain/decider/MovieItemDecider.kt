package com.ri.movieto.domain.decider

interface MovieItemDecider {
    fun provideBackdropPath(path: String?): String

    fun providePosterPath(path: String?): String

    fun provideRoundedAverage(vote_average: Double): String

    fun provideReleaseYear(date: String): String
}