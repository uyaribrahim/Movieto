package com.ri.movieto.presentation.search

import com.ri.movieto.presentation.state.MovieUIItem

data class SearchFragmentViewState (
    val data: List<MovieUIItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
){
}