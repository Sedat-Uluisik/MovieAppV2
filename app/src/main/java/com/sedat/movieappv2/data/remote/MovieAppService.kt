package com.sedat.movieappv2.data.remote

import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppService {
    //https://api.themoviedb.org/3/movie/popular?api_key=api_key&language=tr&page=1
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>
}