package com.sedat.movieappv2.domain.repository

import androidx.paging.PagingData
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(page: Int): Resource<Movie>
}