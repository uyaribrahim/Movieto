package com.ri.movieto.domain.use_case.get_movie_reviews

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.model.ReviewResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val errorHandler: ErrorHandler,
) {
    operator fun invoke(id: Int): Flow<Resource<ReviewResponse>> = flow {

        try {
            emit(Resource.Loading())
            val reviewResponse = repository.getMovieReviews(id).toDomain()
            Log.e("REVIEW", reviewResponse.toString())
            emit(Resource.Success(reviewResponse))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}