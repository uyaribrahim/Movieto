package com.ri.movieto.presentation.search

import com.ri.movieto.domain.model.MovieResponse

data class SearchFragmentViewState (
     val data: MovieResponse? = null,
     val isLoading: Boolean = false,
     val error: String = ""
){
    fun getMovies(): MovieResponse? = data

    fun getNowPlayingMoviesTitle() = "Now Playing"

}