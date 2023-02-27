package com.ri.movieto.domain.use_case.get_movide_detail

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.movie_detail.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider
) {
    operator fun invoke(id: Int): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetail = repository.getMovieDetails(id).toDomain(decider)
            emit(Resource.Success(movieDetail))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    private fun handleError(e: Exception): Resource<MovieDetail> {
        return when (e) {
            is HttpException -> Resource.Error(e.localizedMessage ?: "Beklenmeyen bir hata oluştu")
            is IOException -> Resource.Error("Lütfen internet bağlantınızı kontrol edin")
            else -> Resource.Error("Beklenmeyen bir hata oluştu")
        }
    }
}