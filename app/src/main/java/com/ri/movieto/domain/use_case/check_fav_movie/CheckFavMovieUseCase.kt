package com.ri.movieto.domain.use_case.check_fav_movie

import android.util.Log
import com.ri.movieto.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckFavMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> = flow {
        try {
            val result = repository.isFavMovieExist(id)
            emit(result)
        } catch (e: Exception) {
            Log.e("CHECK ERROR:", e.toString())
        }
    }
}