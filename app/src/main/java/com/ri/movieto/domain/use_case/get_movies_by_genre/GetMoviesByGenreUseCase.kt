package com.ri.movieto.domain.use_case.get_movies_by_genre

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val errorHandler: ErrorHandler,
    private val decider: MovieDecider
) {
    operator fun invoke(category_id: Int, sort: String): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val movieResponse = repository.getMoviesByCategory(category_id, sort).toDomain(decider)
            emit(Resource.Success(movieResponse))
        } catch (e: Exception) {
            emit(Resource.Error(errorHandler.getErrorMessage(e)))
        }
    }
}