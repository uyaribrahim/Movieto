package com.ri.movieto.domain.use_case.get_movide_detail

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.GenreResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.MovieDetailDto
import com.ri.movieto.domain.mapper.Mapper
import com.ri.movieto.domain.model.MovieDetail
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val dtoMapper: Mapper<MovieDetailDto, MovieDetail>
) {
    operator fun invoke(id: Int): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading())
            val movieDetailDto = repository.getMovieDetails(id)
            Log.e("###",movieDetailDto.toString())
            val movieDetail = dtoMapper.mapFrom(movieDetailDto)
            emit(Resource.Success(movieDetail))
        } catch (e: Exception) {
            Log.e("####", e.toString())
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