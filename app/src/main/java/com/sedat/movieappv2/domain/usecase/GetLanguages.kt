package com.sedat.movieappv2.domain.usecase

import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource

class GetLanguages(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Resource<List<LanguageItem>> = movieRepository.getLanguages()
}