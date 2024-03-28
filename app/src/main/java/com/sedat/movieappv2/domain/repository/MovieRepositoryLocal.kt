package com.sedat.movieappv2.domain.repository

import com.sedat.movieappv2.data.local.entity.MovieEntity

interface MovieRepositoryLocal {
    suspend fun getFavourites(size: Int): List<MovieEntity>
    suspend fun saveFavourite(movieEntity: MovieEntity)
    suspend fun deleteFavourite(movieEntity: MovieEntity)
    suspend fun deleteFavouriteWithId(id: Int)
    suspend fun getFavouriteWithId(id: Int): MovieEntity
}