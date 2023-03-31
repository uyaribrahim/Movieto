package com.ri.movieto.data.repository

import android.util.Log
import com.ri.movieto.data.local.MovieDao
import com.ri.movieto.data.local.entity.toGenre
import com.ri.movieto.data.local.entity.toGenreEntity
import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.remote.dto.*
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.repository.MovieRepository
import java.util.Date
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieAPI,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getTrendingMovies(): MovieResponseDto = api.getTrendingMovies()
    override suspend fun getTopRatedMovies(): MovieResponseDto = api.getTopRatedMovies()
    override suspend fun getNowPlayingMovies(): MovieResponseDto = api.getNowPlayingMovies()

    override suspend fun getMovieGenres(): GenreResponse {
        val localMovieGenres = dao.getGenres().map { it.toGenre() }

        return if (localMovieGenres.isEmpty()) {
            api.getMovieGenres().toDomain().apply {
                genres = listOf(GenreResponse.Genre(0, "All")) + genres
                dao.insertGenreResponse(genres.map { it.toGenreEntity() })
            }
        } else {
            GenreResponse(localMovieGenres)
        }
    }

    override suspend fun getMovieDetails(id: Int): MovieDetailDto = api.getMovieDetails(id)
    override suspend fun getMovieCredits(id: Int): CreditDto = api.getMovieCredits(id)
    override suspend fun getMovieRecommend(id: Int): RecommendDto = api.getMovieRecommend(id)
    override suspend fun getMovieReviews(id: Int): ReviewResponseDto = api.getMovieReviews(id)
    override suspend fun getMoviesByCategory(category_id: Int, sort: String): MovieResponseDto =
        api.getMoviesByCategory(category_id, sort)

    override suspend fun searchMovie(query: String): MovieResponseDto = api.searchMovie(query)

}