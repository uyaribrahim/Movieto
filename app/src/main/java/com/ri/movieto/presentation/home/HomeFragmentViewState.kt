package com.ri.movieto.presentation.home

import com.ri.movieto.presentation.state.MovieUIItem

data class HomeFragmentViewState(
    private val trendingMovies: List<MovieUIItem>?,
    private val topRatedMovies: List<MovieUIItem>?,
){
    fun getTrendingMovies(): List<MovieUIItem>? = trendingMovies

    fun getTopRatedMovies(): List<MovieUIItem>? = topRatedMovies

    fun getTrendingMoviesTitle() = "Trending movies"

    fun getTopRatedMoviesTitle() = "Top Rated"
}
