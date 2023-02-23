package com.ri.movieto.data.remote

import com.ri.movieto.BuildConfig
import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("3/movie/popular?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US&page=1")
    suspend fun getTrendingMovies(): MovieResponseDto

    @GET("3/movie/top_rated?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US&page=1")
    suspend fun getTopRatedMovies(): MovieResponseDto

    @GET("3/genre/movie/list?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US")
    suspend fun getMovieGenres(): GenreResponseDto

    @GET("3/movie/{id}?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetailDto

}