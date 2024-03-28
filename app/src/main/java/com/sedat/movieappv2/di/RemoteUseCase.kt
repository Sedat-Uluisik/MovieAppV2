package com.sedat.movieappv2.di

import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.domain.usecase.remote.GetLanguages
import com.sedat.movieappv2.domain.usecase.remote.GetMovieImages
import com.sedat.movieappv2.domain.usecase.remote.GetMovieList
import com.sedat.movieappv2.domain.usecase.remote.GetMovieWithID
import com.sedat.movieappv2.domain.usecase.remote.GetTrendMovies
import com.sedat.movieappv2.domain.usecase.remote.SearchMovie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteUseCase {
    @Singleton
    @Provides
    fun provideGetMovieListUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): GetMovieList = GetMovieList(movieRepositoryRemote)


    @Singleton
    @Provides
    fun provideGetLanguagesUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): GetLanguages = GetLanguages(movieRepositoryRemote)

    @Singleton
    @Provides
    fun provideSearchMovieUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): SearchMovie = SearchMovie(movieRepositoryRemote)

    @Singleton
    @Provides
    fun provideTrendMovieUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): GetTrendMovies = GetTrendMovies(movieRepositoryRemote)

    @Singleton
    @Provides
    fun provideMovieWithIDUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): GetMovieWithID = GetMovieWithID(movieRepositoryRemote)

    @Singleton
    @Provides
    fun provideMovieImagesUseCase(
        movieRepositoryRemote: MovieRepositoryRemote
    ): GetMovieImages = GetMovieImages(movieRepositoryRemote)
}