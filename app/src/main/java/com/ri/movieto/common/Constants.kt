package com.ri.movieto.common

import com.ri.movieto.BuildConfig
import com.ri.movieto.data.model.GenreResponse
import java.util.Objects

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/"
    const val BACKDROP_PATH = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2"
    const val POSTER_PATH = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    const val PROFILE_PATH = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2"
    const val API_KEY = "api_key=${BuildConfig.TMDB_API_KEY}"
}