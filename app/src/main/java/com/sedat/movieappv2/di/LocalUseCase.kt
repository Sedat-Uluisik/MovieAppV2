package com.sedat.movieappv2.di

import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal
import com.sedat.movieappv2.domain.usecase.local.DeleteFavourite
import com.sedat.movieappv2.domain.usecase.local.DeleteFavouriteWithId
import com.sedat.movieappv2.domain.usecase.local.GetFavouriteWithId
import com.sedat.movieappv2.domain.usecase.local.GetFavourites
import com.sedat.movieappv2.domain.usecase.local.SaveFavourite
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalUseCase {


    @Singleton
    @Provides
    fun provideGetFavourites(
        movieRepositoryLocal: MovieRepositoryLocal
    ): GetFavourites = GetFavourites(movieRepositoryLocal)

    @Singleton
    @Provides
    fun provideGetFavouriteWithId(
        movieRepositoryLocal: MovieRepositoryLocal
    ): GetFavouriteWithId = GetFavouriteWithId(movieRepositoryLocal)

    @Singleton
    @Provides
    fun provideSaveFavourite(
        movieRepositoryLocal: MovieRepositoryLocal
    ): SaveFavourite = SaveFavourite(movieRepositoryLocal)

    @Singleton
    @Provides
    fun provideDeleteFavourite(
        movieRepositoryLocal: MovieRepositoryLocal
    ): DeleteFavourite = DeleteFavourite(movieRepositoryLocal)

    @Singleton
    @Provides
    fun provideDeleteFavouriteWithId(
        movieRepositoryLocal: MovieRepositoryLocal
    ): DeleteFavouriteWithId = DeleteFavouriteWithId(movieRepositoryLocal)
}