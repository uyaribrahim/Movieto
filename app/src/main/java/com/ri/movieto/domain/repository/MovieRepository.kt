package com.ri.movieto.domain.repository

import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto

interface MovieRepository {

    suspend fun getTrendingMovies(): MovieResponseDto
    suspend fun getTopRatedMovies(): MovieResponseDto
    suspend fun getMovieGenres(): GenreResponseDto
    suspend fun getMovieDetails(id: Int): MovieDetailDto
}