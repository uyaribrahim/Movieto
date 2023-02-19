package com.ri.movieto.domain.use_case.get_trending_movies

import android.util.Log
import androidx.lifecycle.asLiveData
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.mapper.MovieResponseDtoToDomain
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val dtoMapper: MovieResponseDtoToDomain
) {
    operator fun invoke(): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val movieResponseDto = repository.getTrendingMovies()
            val movieResponse = dtoMapper.mapFrom(movieResponseDto)
            emit(Resource.Success(movieResponse))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    private fun handleError(e: Exception): Resource<MovieResponse> {
        return when (e) {
            is HttpException -> Resource.Error(e.localizedMessage ?: "Beklenmeyen bir hata oluştu")
            is IOException -> Resource.Error("Lütfen internet bağlantınızı kontrol edin")
            else -> Resource.Error("Beklenmeyen bir hata oluştu")
        }
    }
}