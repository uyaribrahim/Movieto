package com.ri.movieto.presentation.home

import com.ri.movieto.domain.model.MovieResponse

data class TopRatedMoviesState(
    val isLoading: Boolean = false,
    val response: MovieResponse? = null,
    val error: String = ""
)
