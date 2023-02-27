package com.ri.movieto.domain.use_case.get_movie_genres

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<GenreResponse>> = flow {
        try {
            emit(Resource.Loading())
            val genreResponse = repository.getMovieGenres().toDomain()
            genreResponse.genres = listOf(GenreResponse.Genre(0,"All")) + genreResponse.genres
            emit(Resource.Success(genreResponse))
        } catch (e: Exception) {
            emit(handleError(e))
        }
    }

    private fun handleError(e: Exception): Resource<GenreResponse> {
        return when (e) {
            is HttpException -> Resource.Error(e.localizedMessage ?: "Beklenmeyen bir hata oluştu")
            is IOException -> Resource.Error("Lütfen internet bağlantınızı kontrol edin")
            else -> Resource.Error("Beklenmeyen bir hata oluştu")
        }
    }
}