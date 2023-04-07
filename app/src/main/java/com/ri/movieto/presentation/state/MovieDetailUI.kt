package com.ri.movieto.presentation.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailUI(
    val id: Int,
    val title: String,
    val movie_label: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: String,
    val trailer_key: String,
    val clip_key: String,
    val tagline: String,
    val release_date: Long,
): Parcelable