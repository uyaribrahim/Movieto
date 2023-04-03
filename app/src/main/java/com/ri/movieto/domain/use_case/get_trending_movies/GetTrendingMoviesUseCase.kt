package com.ri.movieto.domain.use_case.get_trending_movies

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val movieResponse = repository.getTrendingMovies().toDomain()
            emit(Resource.Success(movieResponse))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}