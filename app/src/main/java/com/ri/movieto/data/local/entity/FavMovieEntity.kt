package com.ri.movieto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ri.movieto.domain.model.MovieResponse

@Entity(tableName = "favorite_movies")
data class FavMovieEntity(
    @PrimaryKey
    val id: Int,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: String,

    )

fun FavMovieEntity.toMovie(): MovieResponse.Movie {
    return MovieResponse.Movie(
        id = id,
        title = title,
        vote_average = vote_average.toDouble(),
        backdrop_path = backdrop_path,
        poster_path = poster_path,
        release_date = release_date.toLong(),
    )
}

fun MovieResponse.Movie.toFavMovieEntity(): FavMovieEntity {
    return FavMovieEntity(
        id = id,
        title = title,
        vote_average = vote_average.toString(),
        backdrop_path = backdrop_path,
        poster_path = poster_path,
        release_date = release_date.toString(),

        )
}
