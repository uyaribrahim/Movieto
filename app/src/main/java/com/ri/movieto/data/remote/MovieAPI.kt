package com.ri.movieto.data.remote

import com.ri.movieto.BuildConfig
import com.ri.movieto.common.Constants
import com.ri.movieto.data.remote.dto.*
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular?${Constants.API_KEY_WITH_PREFIX}&language=en-US&page=1")
    suspend fun getTrendingMovies(): MovieResponseDto

    @GET("movie/top_rated?${Constants.API_KEY_WITH_PREFIX}&language=en-US&page=1")
    suspend fun getTopRatedMovies(): MovieResponseDto

    @GET("genre/movie/list?${Constants.API_KEY_WITH_PREFIX}&language=en-US")
    suspend fun getMovieGenres(): GenreResponseDto

    @GET("movie/{id}?${Constants.API_KEY_WITH_PREFIX}&language=en-US&append_to_response=videos")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetailDto

    @GET("movie/{id}/credits?${Constants.API_KEY_WITH_PREFIX}&language=en-US")
    suspend fun getMovieCredits(@Path("id") id: Int): CreditDto

    @GET("movie/{id}/recommendations?${Constants.API_KEY_WITH_PREFIX}&language=en-US&page=1")
    suspend fun getMovieRecommend(@Path("id") id: Int): RecommendDto

    @GET("movie/{id}/reviews?${Constants.API_KEY_WITH_PREFIX}&language=en-US&page=1")
    suspend fun getMovieReviews(@Path("id") id: Int): ReviewResponseDto

    @GET("discover/movie?${Constants.API_KEY_WITH_PREFIX}&language=en-US&include_adult=false&include_video=false&page=1")
    suspend fun getMoviesByCategory(@Query("with_genres") category_id: Int, @Query("sort_by") sort: String ): MovieResponseDto


}