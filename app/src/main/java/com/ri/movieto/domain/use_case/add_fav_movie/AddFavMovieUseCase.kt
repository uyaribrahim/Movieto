package com.ri.movieto.domain.use_case.add_fav_movie

import android.util.Log
import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import javax.inject.Inject

class AddFavMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(movie: MovieResponse.Movie) {
        try {
            repository.addFavMovie(movie)
        } catch (e: Exception) {
            Log.e("ADD ERROR: ", e.toString())
        }
    }
}