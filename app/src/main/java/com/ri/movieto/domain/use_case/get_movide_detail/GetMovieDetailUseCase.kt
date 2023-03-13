package com.ri.movieto.domain.use_case.get_movide_detail

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.movie_detail.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(id: Int): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetails(id).toDomain(decider)
            emit(Resource.Success(movieDetail))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}