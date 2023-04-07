package com.ri.movieto.domain.use_case.get_favorite_movies

import android.util.Log
import com.ri.movieto.common.Resource
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<MovieResponse.Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getFavMovies()
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            Log.e("ERROR:", e.toString())
            emit(Resource.Error(e.toString()))
        }
    }
}