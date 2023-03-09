package com.ri.movieto.data.remote.dto

import com.ri.movieto.domain.decider.MovieDecider
import com.ri.movieto.domain.model.Credit

data class CreditDto(
    val cast: List<CastDto>,
    val crew: List<CrewDto>,
    val id: Int
) {
    data class CastDto(
        val adult: Boolean,
        val cast_id: Int,
        val character: String,
        val credit_id: String,
        val gender: Int? = null,
        val id: Int,
        val known_for_department: String,
        val name: String,
        val order: Int,
        val original_name: String,
        val popularity: Double,
        val profile_path: String? = null
    )

    data class CrewDto(
        val adult: Boolean,
        val credit_id: String,
        val department: String,
        val gender: Int? = null,
        val id: Int,
        val job: String,
        val known_for_department: String,
        val name: String,
        val original_name: String,
        val popularity: Double,
        val profile_path: String? = null
    )
}

fun CreditDto.toDomain(decider: MovieDecider): Credit {
    return Credit(
        cast = cast.map { person ->
            Credit.Cast(
                id = person.id,
                character = person.character,
                name = person.name,
                profilePath = decider.provideProfilePath(person.profile_path)
                )
        }
    )
}