package com.sedat.movieappv2.di


import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.sedat.movieappv2.BuildConfig
import com.sedat.movieappv2.data.local.AppDatabase
import com.sedat.movieappv2.data.remote.MovieAppService
import com.sedat.movieappv2.data.repository.MovieRepositoryLocalImpl
import com.sedat.movieappv2.data.repository.MovieRepositoryRemoteImpl
import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
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
    fun provideMovieRepositoryRemote(
        service: MovieAppService,
        db: AppDatabase
    ): MovieRepositoryRemote = MovieRepositoryRemoteImpl(service, db)

    @Singleton
    @Provides
    fun provideMovieRepositoryLocal(
        db: AppDatabase
    ): MovieRepositoryLocal = MovieRepositoryLocalImpl(db)

}