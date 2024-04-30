package com.sedat.movieappv2.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sedat.movieappv2.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM FAVOURITES WHERE isFavourite = 1")
    fun getFavourites(): List<MovieEntity>

    @Query("SELECT * FROM FAVOURITES WHERE isFavourite = 0 ORDER BY createdAt, releaseDate DESC")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM FAVOURITES WHERE movieId = :id AND isFavourite = 1")
    suspend fun getFavouriteWithId(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavourite(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteFavourite(movieEntity: MovieEntity)

    @Query("DELETE FROM FAVOURITES WHERE movieId =:id AND isFavourite = 1")
    suspend fun deleteFavouriteWithId(id: Int)

    @Query("DELETE FROM FAVOURITES WHERE isFavourite = 0")
    suspend fun deleteMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieEntity>)
}