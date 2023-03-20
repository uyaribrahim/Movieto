package com.ri.movieto.common

import com.ri.movieto.BuildConfig

object Constants {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BACKDROP_PATH = "https://www.themoviedb.org/t/p/w533_and_h300_bestv2"
    const val POSTER_PATH = "https://www.themoviedb.org/t/p/w600_and_h900_bestv2"
    const val PROFILE_PATH = "https://www.themoviedb.org/t/p/w300_and_h450_bestv2"
    const val AVATAR_PATH = "https://www.themoviedb.org/t/p/w64_and_h64_face"
    const val API_KEY_WITH_PREFIX = "api_key=${BuildConfig.TMDB_API_KEY}"
}