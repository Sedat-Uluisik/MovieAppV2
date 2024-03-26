package com.sedat.movieappv2.domain.repository

import androidx.paging.PagingData
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource<Movie>
    suspend fun getLanguages(): Resource<List<LanguageItem>>
    suspend fun searchMovie(query: String): Resource<Movie>
    suspend fun getTrendMovies(time: String, page: Int): Resource<Movie>
    suspend fun getMovie(movieId: Int): Resource<Result>
    suspend fun getMovieImages(movieId: Int): Resource<MovieImages>
}