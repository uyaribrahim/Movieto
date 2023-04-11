package com.ri.movieto.presentation.favorites

import com.ri.movieto.presentation.state.MovieUIItem

data class FavoritesFragmentViewState(
    val data: List<MovieUIItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)