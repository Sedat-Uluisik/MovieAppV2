package com.sedat.movieappv2.domain.usecase.local

import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.domain.repository.MovieRepositoryLocal
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource

class DeleteFavourite(
    private val movieRepositoryLocal: MovieRepositoryLocal
) {
    suspend operator fun invoke(movieEntity: MovieEntity) = movieRepositoryLocal.deleteFavourite(movieEntity)
}