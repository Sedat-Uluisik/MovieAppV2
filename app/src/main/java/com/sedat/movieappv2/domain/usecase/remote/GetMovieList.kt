package com.sedat.movieappv2.domain.usecase.remote

import androidx.paging.PagingData
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMovieList(
    private val movieRepositoryRemote: MovieRepositoryRemote
) {
    operator fun invoke(): Flow<PagingData<Result>> = movieRepositoryRemote.getMovies()
}