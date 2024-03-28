package com.sedat.movieappv2.domain.usecase.local

import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal

class DeleteFavouriteWithId(
    private val movieRepositoryLocal: MovieRepositoryLocal
) {
    suspend operator fun invoke(id: Int) = movieRepositoryLocal.deleteFavouriteWithId(id)
}