package com.sedat.movieappv2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sedat.movieappv2.data.local.dao.MovieDao
import com.sedat.movieappv2.data.local.dao.RemoteKeyDao
import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.data.local.entity.RemoteKeyEntity

@Database(entities = [MovieEntity::class, RemoteKeyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val remoteKeyDao: RemoteKeyDao
}