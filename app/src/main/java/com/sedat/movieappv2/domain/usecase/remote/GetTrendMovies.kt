package com.sedat.movieappv2.domain.usecase.remote

import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource

class GetTrendMovies(
    private val movieRepositoryRemote: MovieRepositoryRemote
) {
    suspend operator fun invoke(time: String, page: Int): Resource<Movie> = movieRepositoryRemote.getTrendMovies(time, page)
}