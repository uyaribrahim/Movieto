package com.ri.movieto.data.service

import com.ri.movieto.BuildConfig
import com.ri.movieto.data.model.GenreResponse
import com.ri.movieto.data.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface MediaAPI {

    @GET("3/movie/popular?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US&page=1")
    fun getTrendingMovies(): Single<MovieResponse>

    @GET("3/genre/movie/list?api_key=${BuildConfig.TMDB_API_KEY}&language=en-US")
    fun getMovieGenres(): Single<GenreResponse>

}