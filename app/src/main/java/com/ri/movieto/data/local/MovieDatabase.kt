package com.ri.movieto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ri.movieto.data.local.entity.GenreEntity

@Database(
    entities = [GenreEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract val dao: MovieDao
}