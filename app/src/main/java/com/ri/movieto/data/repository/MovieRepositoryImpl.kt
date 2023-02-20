package com.ri.movieto.data.repository

import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAPI
) : MovieRepository {

    override suspend fun getTrendingMovies(): MovieResponseDto = api.getTrendingMovies()
    override suspend fun getTopRatedMovies(): MovieResponseDto = api.getTopRatedMovies()

}