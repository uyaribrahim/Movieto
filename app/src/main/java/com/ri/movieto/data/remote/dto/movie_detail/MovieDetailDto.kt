package com.ri.movieto.data.remote.dto.movie_detail

import com.ri.movieto.common.Constants
import com.ri.movieto.data.remote.dto.VideoResponseDto
import com.ri.movieto.domain.model.GenreResponse
import com.ri.movieto.domain.model.MovieDetail
import java.text.SimpleDateFormat
import java.util.*

data class MovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String?,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<GenreDto>,
    val homepage: String?,
    val id: Int,
    val imdb_id: String?,
    val original_language: String,
    val original_title: String,
    val overview: String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Long,
    val runtime: Int?,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val videos: VideoResponseDto
)

fun MovieDetailDto.toDomain(): MovieDetail {
    val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = release_date.let { inputSDF.parse(it) }
    val milliseconds = date?.time ?: 0

    val firstTwoGenres = genres.take(2)
    var label = ""
    firstTwoGenres.forEach { genre -> label += genre.name + " / " }
    val c = Calendar.getInstance()
    c.timeInMillis = milliseconds
    val year = c.get(Calendar.YEAR).toString()

    val movieLabel = "$label$year"

    val trailerKey = provideVideoKey(videos.results, "Trailer")
    val clipKey = provideVideoKey(videos.results, "Clip")

    return MovieDetail(
        id = id,
        overview = overview ?: "",
        movie_label = movieLabel,
        title = title,
        poster_path = "${Constants.POSTER_PATH}${poster_path}",
        backdrop_path = "${Constants.BACKDROP_PATH}${backdrop_path}",
        vote_average = vote_average,
        tagline = tagline ?: "",
        release_date = milliseconds,
        trailer_key = trailerKey,
        clip_key = clipKey
    )
}

fun provideVideoKey(videos: List<VideoResponseDto.VideoDto>, type: String): String {
    var youtubeVideos = videos.filter { video ->
        video.type == type && video.site == "YouTube"
    }
    if (youtubeVideos.isEmpty()) {
        youtubeVideos = videos.filter { video ->
            video.site == "YouTube"
        }
    }
    val video = youtubeVideos.elementAt(0)
    return video.key
}