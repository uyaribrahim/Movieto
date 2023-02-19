package com.ri.movieto.domain.repository

import com.ri.movieto.data.remote.dto.MovieResponseDto

interface MovieRepository {

    suspend fun getTrendingMovies(): MovieResponseDto
}