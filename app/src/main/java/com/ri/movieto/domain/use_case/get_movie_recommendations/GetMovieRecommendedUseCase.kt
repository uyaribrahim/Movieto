package com.ri.movieto.domain.use_case.get_movie_recommendations

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.Recommend
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieRecommendedUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(id: Int): Flow<Resource<Recommend>> = flow {
        try {
            emit(Resource.Loading())
            val similar = repository.getMovieRecommend(id).toDomain(decider)
            emit(Resource.Success(similar))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}