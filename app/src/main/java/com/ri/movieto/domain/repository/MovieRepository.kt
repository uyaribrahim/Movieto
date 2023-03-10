package com.ri.movieto.domain.repository

import com.ri.movieto.data.remote.dto.CreditDto
import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.SimilarDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto

interface MovieRepository {

    suspend fun getTrendingMovies(): MovieResponseDto
    suspend fun getTopRatedMovies(): MovieResponseDto
    suspend fun getMovieGenres(): GenreResponseDto
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun getMovieCredits(id: Int): CreditDto
    suspend fun getMovieSimilar(id: Int): SimilarDto
}