package com.ri.movieto.presentation.detail

import com.ri.movieto.domain.model.MovieResponse

data class MovieDetailState (
    val isLoading: Boolean = false,
    val response: MovieResponse? = null,
    val error: String = ""
)