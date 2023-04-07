package com.ri.movieto.data.local

import androidx.room.*
import com.ri.movieto.data.local.entity.FavMovieEntity
import com.ri.movieto.data.local.entity.GenreEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenreResponse(list: List<GenreEntity>)

    @Query("SELECT * FROM genreentity ORDER BY id = 0 DESC, name ASC")
    suspend fun getGenres(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(movie: FavMovieEntity)

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavMovies(): List<FavMovieEntity>

    @Query("SELECT EXISTS(SELECT * FROM favorite_movies WHERE id = :id)")
    fun isFavMovieExist(id : Int) : Boolean

    @Delete
    fun deleteFavMovie(movie: FavMovieEntity)

}