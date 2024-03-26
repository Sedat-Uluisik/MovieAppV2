package com.sedat.movieappv2.di


import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.sedat.movieappv2.BuildConfig
import com.sedat.movieappv2.data.local.AppDatabase
import com.sedat.movieappv2.data.remote.MovieAppService
import com.sedat.movieappv2.data.repository.MovieRepositoryImpl
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.domain.usecase.GetLanguages
import com.sedat.movieappv2.domain.usecase.GetMovieImages
import com.sedat.movieappv2.domain.usecase.GetMovieList
import com.sedat.movieappv2.domain.usecase.GetMovieWithID
import com.sedat.movieappv2.domain.usecase.GetTrendMovies
import com.sedat.movieappv2.domain.usecase.SearchMovie
import com.sedat.movieappv2.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                }
            )
        }.build()

    @Singleton
    @Provides
    fun provideRickAndMortyApi(okHttpClient: OkHttpClient): MovieAppService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAppService::class.java)

    @Singleton
    @Provides
    fun provideMovieAppDatabase(
        app: Application
    ): AppDatabase =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "movieappdb"
        ).fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    @ExperimentalPagingApi
    fun provideMovieRepository(
        service: MovieAppService
    ): MovieRepository = MovieRepositoryImpl(service)

    @Singleton
    @Provides
    fun provideGetMovieListUseCase(
        movieRepository: MovieRepository
    ): GetMovieList = GetMovieList(movieRepository)


    @Singleton
    @Provides
    fun provideGetLanguagesUseCase(
        movieRepository: MovieRepository
    ): GetLanguages = GetLanguages(movieRepository)

    @Singleton
    @Provides
    fun provideSearchMovieUseCase(
        movieRepository: MovieRepository
    ): SearchMovie = SearchMovie(movieRepository)

    @Singleton
    @Provides
    fun provideTrendMovieUseCase(
        movieRepository: MovieRepository
    ): GetTrendMovies = GetTrendMovies(movieRepository)

    @Singleton
    @Provides
    fun provideMovieWithIDUseCase(
        movieRepository: MovieRepository
    ): GetMovieWithID = GetMovieWithID(movieRepository)

    @Singleton
    @Provides
    fun provideMovieImagesUseCase(
        movieRepository: MovieRepository
    ): GetMovieImages = GetMovieImages(movieRepository)
}