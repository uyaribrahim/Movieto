package com.ri.movieto.domain.use_case.get_movie_credits

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.Credit
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(id: Int): Flow<Resource<Credit>> = flow {
        try {
            emit(Resource.Loading())
            val credits = repository.getMovieCredits(id).toDomain(decider)
            emit(Resource.Success(credits))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}