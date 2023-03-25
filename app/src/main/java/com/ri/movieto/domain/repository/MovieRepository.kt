package com.ri.movieto.domain.repository

import com.ri.movieto.data.remote.dto.*
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.model.GenreResponse
import java.util.*

interface MovieRepository {

    suspend fun getTrendingMovies(): MovieResponseDto
    suspend fun getTopRatedMovies(): MovieResponseDto
    suspend fun getMovieGenres(): GenreResponse
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun getMovieCredits(id: Int): CreditDto
    suspend fun getMovieRecommend(id: Int): RecommendDto
    suspend fun getMovieReviews(id: Int): ReviewResponseDto
    suspend fun getMoviesByCategory(category_id: Int, sort: String): MovieResponseDto
}