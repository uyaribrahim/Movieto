package com.ri.movieto.presentation.home

import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse

data class HomeFragmentViewState(
    private val trendingMovies: MovieResponse?,
    private val topRatedMovies: MovieResponse?,
    private val genres: GenreResponse?,
){
    fun getTrendingMovies(): MovieResponse? = trendingMovies

    fun getTopRatedMovies(): MovieResponse? = topRatedMovies

    fun getGenres(): GenreResponse? = genres

    fun getTrendingMoviesTitle() = "Trending movies"

    fun getTopRatedMoviesTitle() = "Top Rated"
}
