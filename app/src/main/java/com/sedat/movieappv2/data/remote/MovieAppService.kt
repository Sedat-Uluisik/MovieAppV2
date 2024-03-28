package com.sedat.movieappv2.data.remote

import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAppService {
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Movie

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

    @GET("/3/trending/movie/{time_window}")
    suspend fun getTrendMovies(
        @Path("time_window") time: String,
        @Query("language") language: String,
        @Query("region") region: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Movie>

    @GET("/3/movie/{movie_id}?")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Result>

    @GET("/3/movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ):Response<MovieImages>
}