package com.ri.movieto.data.repository

import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.remote.dto.*
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAPI
) : MovieRepository {

    override suspend fun getTrendingMovies(): MovieResponseDto = api.getTrendingMovies()
    override suspend fun getTopRatedMovies(): MovieResponseDto = api.getTopRatedMovies()
    override suspend fun getMovieGenres(): GenreResponseDto = api.getMovieGenres()
    override suspend fun getMovieDetails(id: Int): MovieDetailDto = api.getMovieDetails(id)
    override suspend fun getMovieCredits(id: Int): CreditDto = api.getMovieCredits(id)
    override suspend fun getMovieRecommend(id: Int): RecommendDto = api.getMovieRecommend(id)
    override suspend fun getMovieReviews(id: Int): ReviewResponseDto = api.getMovieReviews(id)
    override suspend fun getMoviesByCategory(category_id: Int, sort: String): MovieResponseDto = api.getMoviesByCategory(category_id,sort)

}