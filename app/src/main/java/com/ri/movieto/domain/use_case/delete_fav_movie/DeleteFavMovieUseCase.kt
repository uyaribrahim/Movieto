package com.ri.movieto.domain.use_case.delete_fav_movie

import com.ri.movieto.domain.model.MovieResponse
import com.ri.movieto.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteFavMovieUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieResponse.Movie){
        repository.deleteFavMovie(movie)
    }
}