package com.ri.movieto.data.service

import com.ri.movieto.data.model.GenreResponse
import com.ri.movieto.data.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MediaAPIService {

    private val BASE_URL = "https://api.themoviedb.org/"

    private val api =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(MediaAPI::class.java)

    fun getTrendingMovies(): Single<MovieResponse> {
        return api.getTrendingMovies()
    }

    fun getMovieGenres(): Single<GenreResponse> {
        return api.getMovieGenres()
    }
}