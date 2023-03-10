package com.ri.movieto.domain.use_case.get_movie_recommendations

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.Recommend
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieRecommendedUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val decider: MovieDecider
) {
    operator fun invoke(id: Int): Flow<Resource<Recommend>> = flow {
        try {
            emit(Resource.Loading())
            val similar = repository.getMovieSimilar(id).toDomain(decider)
            emit(Resource.Success(similar))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    private fun handleError(e: Exception): Resource<Recommend> {
        return when (e) {
            is HttpException -> Resource.Error(e.localizedMessage ?: "Beklenmeyen bir hata oluştu")
            is IOException -> Resource.Error("Lütfen internet bağlantınızı kontrol edin")
            else -> Resource.Error("Beklenmeyen bir hata oluştu")
        }
    }
}