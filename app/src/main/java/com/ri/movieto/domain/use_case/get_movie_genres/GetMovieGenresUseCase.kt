package com.ri.movieto.domain.use_case.get_movie_genres

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(): Flow<Resource<GenreResponse>> = flow {
        try {
            emit(Resource.Loading())
            val genreResponse = repository.getMovieGenres()
            emit(Resource.Success(genreResponse))
        } catch (e: Exception) {
            val error = errorHandler.getErrorMessage(e)
            emit(Resource.Error(error))
        }
    }
}