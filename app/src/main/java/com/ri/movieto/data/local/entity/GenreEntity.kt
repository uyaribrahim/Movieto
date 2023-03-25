package com.ri.movieto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ri.movieto.domain.model.GenreResponse

@Entity
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String
)

fun GenreEntity.toGenre(): GenreResponse.Genre {
    return GenreResponse.Genre(
        id = id,
        name = name
    )
}

fun GenreResponse.Genre.toGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id,
        name = name
    )
}