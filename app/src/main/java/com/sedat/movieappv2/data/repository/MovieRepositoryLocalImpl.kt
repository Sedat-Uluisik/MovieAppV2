package com.sedat.movieappv2.data.repository

import com.sedat.movieappv2.data.local.AppDatabase
import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal

class MovieRepositoryLocalImpl(
    private val db: AppDatabase
): MovieRepositoryLocal {
    override suspend fun getFavourites(size: Int): List<MovieEntity> {
        return db.movieDao.getFavourites(size)
    }

    override suspend fun saveFavourite(movieEntity: MovieEntity) {
        db.movieDao.saveFavourite(movieEntity)
    }

    override suspend fun deleteFavourite(movieEntity: MovieEntity) {
        db.movieDao.deleteFavourite(movieEntity)
    }

    override suspend fun deleteFavouriteWithId(id: Int) {
        db.movieDao.deleteFavouriteWithId(id)
    }

    override suspend fun getFavouriteWithId(id: Int): MovieEntity? {
        return db.movieDao.getFavouriteWithId(id)
    }

}