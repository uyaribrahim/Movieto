package com.ri.movieto.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ri.movieto.data.local.entity.GenreEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenreResponse(list: List<GenreEntity>)

    @Query("SELECT * FROM genreentity ORDER BY id = 0 DESC, name ASC")
    suspend fun getGenres(): List<GenreEntity>
}