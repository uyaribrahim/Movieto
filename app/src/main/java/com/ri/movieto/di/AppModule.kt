package com.ri.movieto.di

import com.ri.movieto.common.Constants
import com.ri.movieto.data.decider.MovieItemDeciderImpl
import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.MovieResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.data.repository.MovieRepositoryImpl
import com.ri.movieto.domain.decider.MovieItemDecider
import com.ri.movieto.domain.mapper.GenreResponseDtoToDomain
import com.ri.movieto.domain.mapper.Mapper
import com.ri.movieto.domain.mapper.MovieDetailDtoToDomain
import com.ri.movieto.domain.mapper.MovieResponseDtoToDomain
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieApi(): MovieAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MovieAPI
    ): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovieItemDecider(
    ): MovieItemDecider {
        return MovieItemDeciderImpl()
    }

    @Provides
    @Singleton
    fun provideMovieResponseDtoToDomain(
        itemDecider: MovieItemDecider
    ): Mapper<MovieResponseDto, MovieResponse> {
        return MovieResponseDtoToDomain(itemDecider)
    }

    @Provides
    @Singleton
    fun provideGenreResponseDtoToDomain(): Mapper<GenreResponseDto, GenreResponse> {
        return GenreResponseDtoToDomain()
    }

    @Provides
    @Singleton
    fun provideMovieDetailDtoToDomain(decider: MovieItemDecider): Mapper<MovieDetailDto, MovieDetail> {
        return MovieDetailDtoToDomain(decider)
    }

}