package com.ri.movieto.domain.use_case.get_movies_by_genre

import com.ri.movieto.common.Resource
import com.ri.movieto.data.remote.dto.toDomain
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import com.ri.movieto.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val errorHandler: ErrorHandler,
) {
    operator fun invoke(category_id: Int, sort: String): Flow<Resource<MovieResponse>> = flow {
        try {
            emit(Resource.Loading())
            val movieResponse = repository.getMoviesByCategory(category_id, sort).toDomain()
            // Top rated film listesi için filmleri vote_average değerine göre yeniden sıralamak gerekiyor.
            if (sort == "vote_count.desc") {
                val sortedMovieList = movieResponse.movies.sortedByDescending { it.vote_average }
                movieResponse.movies = sortedMovieList
            }
            emit(Resource.Success(movieResponse))
        } catch (e: Exception) {
            emit(Resource.Error(errorHandler.getErrorMessage(e)))
        }
    }
}