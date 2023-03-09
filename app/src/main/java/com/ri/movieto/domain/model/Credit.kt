package com.ri.movieto.domain.model

class Credit(
    val cast: List<Cast>
) {
    data class Cast(
        val id: Int,
        val character: String,
        val name: String,
        val profilePath: String
    )
}