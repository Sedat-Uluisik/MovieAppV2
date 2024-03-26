package com.sedat.movieappv2.domain.usecase

import androidx.paging.PagingData
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendMovies(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(time: String, page: Int): Resource<Movie> = movieRepository.getTrendMovies(time, page)
}