package com.ri.movieto.data.repository

import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.remote.dto.CreditDto
import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.SimilarDto
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
    override suspend fun getMovieSimilar(id: Int): SimilarDto = api.getMovieSimilar(id)

}