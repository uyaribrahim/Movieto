package com.ri.movieto.domain.repository

import com.ri.movieto.data.remote.dto.CreditDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.RecommendDto
import com.ri.movieto.data.remote.dto.ReviewResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieResponse

interface MovieRepository {

    suspend fun getTrendingMovies(): MovieResponseDto
    suspend fun getTopRatedMovies(): MovieResponseDto
    suspend fun getNowPlayingMovies(): MovieResponseDto
    suspend fun getMovieGenres(): GenreResponse
    suspend fun getMovieDetails(id: Int): MovieDetailDto
    suspend fun getMovieCredits(id: Int): CreditDto
    suspend fun getMovieRecommend(id: Int): RecommendDto
    suspend fun getMovieReviews(id: Int): ReviewResponseDto
    suspend fun getMoviesByCategory(category_id: Int, sort: String): MovieResponseDto
    suspend fun searchMovie(query: String): MovieResponseDto
    suspend fun addFavMovie(movie: MovieResponse.Movie)
    suspend fun getFavMovies(): List<MovieResponse.Movie>
    suspend fun isFavMovieExist(id: Int): Boolean
    suspend fun deleteFavMovie(movie: MovieResponse.Movie)
}