package com.ri.movieto.presentation.home

import com.ri.movieto.domain.model.MovieResponse

data class TrendingMoviesState(
    val isLoading: Boolean = false,
    val response: MovieResponse? = null,
    val error: String = ""
)
