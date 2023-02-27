package com.ri.movieto.di

import com.ri.movieto.common.Constants
import com.ri.movieto.data.remote.MovieAPI
import com.ri.movieto.data.repository.MovieRepositoryImpl
import com.ri.movieto.domain.decider.MovieDecider
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
    ): MovieDecider {
        return MovieDecider()
    }


}