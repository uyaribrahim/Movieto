package com.ri.movieto.domain.use_case.search_movie

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(query: String): Flow<Resource<MovieResponse>> = flow {

        try {
            emit(Resource.Loading())
            val movieResponse = repository.searchMovie(query).toDomain(decider)
            emit(Resource.Success(movieResponse))

        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }

    }
}