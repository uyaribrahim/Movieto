package com.ri.movieto.data.remote.dto

import android.util.Log
import com.ri.movieto.common.Constants
import com.ri.movieto.domain.model.MovieResponse
import java.text.SimpleDateFormat
import java.util.*

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
) {
    data class MovieDto(
        val adult: Boolean,
        val backdrop_path: String?,
        val genre_ids: List<Int>,
        val id: Int,
        val original_language: String,
        val original_title: String,
        val overview: String,
        val popularity: Double,
        val poster_path: String?,
        val release_date: String?,
        val title: String,
        val video: Boolean,
        val vote_average: Double,
        val vote_count: Int
    )
}

fun MovieResponseDto.toDomain(): MovieResponse {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return MovieResponse(
        page = page,
        movies = results.map { movie ->
            val date = movie.release_date?.let { inputSDF.parse(it) }
            val milliseconds = date?.time ?: 0
            Log.e("DATE: ", milliseconds.toString())
            MovieResponse.Movie(
                poster_path = "${Constants.POSTER_PATH}${movie.poster_path}",
                backdrop_path = "${Constants.BACKDROP_PATH}${movie.backdrop_path}",
                vote_average = movie.vote_average,
                title = movie.title,
                id = movie.id,
                release_date = milliseconds,
            )
        },
        total_pages = total_pages,
        total_results = total_results
    )
}
