package com.ri.movieto.domain.use_case.get_top_rated_movies

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider
) {
    operator fun invoke(): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val movieResponse = repository.getTopRatedMovies().toDomain(decider)
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