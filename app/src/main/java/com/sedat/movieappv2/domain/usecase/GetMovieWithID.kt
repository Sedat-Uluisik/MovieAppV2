package com.sedat.movieappv2.domain.usecase

import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource

class GetMovieWithID(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<Result> = movieRepository.getMovie(movieId)
}