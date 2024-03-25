package com.sedat.movieappv2.data.remote

import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppService {
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("/3/configuration/languages")
    suspend fun getLanguages(
        @Query("api_key") apiKey: String = API_KEY
    ): Response<List<LanguageItem>>

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ):Response<Movie>
}