package com.sedat.movieappv2.domain.usecase.local

import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal

class SaveFavourite(
    private val movieRepositoryLocal: MovieRepositoryLocal
) {
    suspend operator fun invoke(movieEntity: MovieEntity) = movieRepositoryLocal.saveFavourite(movieEntity)
}