package com.sedat.movieappv2.domain.usecase.remote

import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource

class GetMovieWithID(
    private val movieRepositoryRemote: MovieRepositoryRemote
) {
    suspend operator fun invoke(movieId: Int): Resource<Result> = movieRepositoryRemote.getMovie(movieId)
}