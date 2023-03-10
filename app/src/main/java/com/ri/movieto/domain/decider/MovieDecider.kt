package com.ri.movieto.domain.decider

import com.ri.movieto.common.Constants
import com.ri.movieto.data.remote.dto.VideoResponseDto
import com.ri.movieto.data.remote.dto.movie_detail.Genre
import kotlin.math.roundToInt

class MovieDecider {
    fun provideBackdropPath(path: String?): String {
        return if (path.isNullOrEmpty()) {
            ""
        } else {
            "${Constants.BACKDROP_PATH}$path"
        }
    }

    fun providePosterPath(path: String?): String = "${Constants.POSTER_PATH}$path"

    fun provideProfilePath(path: String?): String = "${Constants.PROFILE_PATH}$path"

    fun provideRoundedAverage(vote_average: Double): String {
        val roundOff = (vote_average * 10.0).roundToInt() / 10.0
        return roundOff.toString()
    }

    fun provideReleaseYear(date: String): String {
        val releaseDateArray = date.split("-")
        return releaseDateArray[0]
    }

    fun provideMovieLabel(date: String, genres: List<Genre>): String {
        val releaseYear = provideReleaseYear(date)
        val firstThreeGenres = genres.take(2)
        var label = ""
        firstThreeGenres.forEach { genre -> label += genre.name + " / " }
        return "$label$releaseYear"
    }

    fun provideVideoKey(videos: VideoResponseDto, type: String): String {
        var youtubeVideos = videos.results.filter { video ->
            video.type == type && video.site == "YouTube"
        }
        if (youtubeVideos.isEmpty()) {
            youtubeVideos = videos.results.filter { video ->
                video.site == "YouTube"
            }
        }
        val video = youtubeVideos.elementAt(0)
        return video.key
    }
}