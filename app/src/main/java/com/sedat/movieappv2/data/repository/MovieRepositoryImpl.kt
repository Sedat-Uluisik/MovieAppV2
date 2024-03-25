package com.sedat.movieappv2.data.repository

import com.sedat.movieappv2.data.remote.MovieAppService
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.domain.repository.MovieRepository
import com.sedat.movieappv2.util.Resource
import javax.inject.Inject

class MovieRepositoryImpl(
    private val movieAppService: MovieAppService
): MovieRepository {
    override suspend fun getMovies(page: Int): Resource<Movie> {
        return try {
            val response = movieAppService.getMovies(page, "en")
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else
                Resource.error("Error", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getLanguages(): Resource<List<LanguageItem>> {
        return try {
            val response = movieAppService.getLanguages()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else
                Resource.error("Error", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun searchMovie(query: String): Resource<Movie> {
        return try {
            val response = movieAppService.searchMovie(query, "en")
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }else
                Resource.error("Error!", null)
        }catch (e:Exception){
            Resource.error("No data!", null)
        }
    }

}