package com.sedat.movieappv2.domain.usecase

import androidx.paging.PagingData
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovie(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(query: String): Resource<Movie> = movieRepository.searchMovie(query)
}