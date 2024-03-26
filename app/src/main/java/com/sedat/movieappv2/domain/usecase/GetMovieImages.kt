package com.sedat.movieappv2.domain.usecase

import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource

class GetMovieImages(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<MovieImages> = movieRepository.getMovieImages(movieId)
}