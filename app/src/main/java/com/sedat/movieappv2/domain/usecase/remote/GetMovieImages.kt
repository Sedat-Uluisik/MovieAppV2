package com.sedat.movieappv2.domain.usecase.remote

import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource

class GetMovieImages(
    private val movieRepositoryRemote: MovieRepositoryRemote
) {
    suspend operator fun invoke(movieId: Int): Resource<MovieImages> = movieRepositoryRemote.getMovieImages(movieId)
}