package com.sedat.movieappv2.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sedat.movieappv2.data.local.AppDatabase
import com.sedat.movieappv2.data.paging.MovieRemoteMediator
import com.sedat.movieappv2.data.remote.MovieAppService
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Movie
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.data.remote.model.imagemodel.MovieImages
import com.sedat.movieappv2.domain.repository.MovieRepositoryRemote
import com.sedat.movieappv2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryRemoteImpl(
    private val movieAppService: MovieAppService,
    private val db: AppDatabase
): MovieRepositoryRemote {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Result>> {
        val pagingSourceFactory = { db.movieDao.getMovies() }

        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true,
            ),
            remoteMediator = MovieRemoteMediator(
                movieAppService,
                db
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { movieEntityPagingData ->
            movieEntityPagingData.map { movieEntity ->
                movieEntity.toResult()
            }
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

    override suspend fun getTrendMovies(time: String, page: Int): Resource<Movie> {
        return try {
            val response = movieAppService.getTrendMovies(time, "en", "en", page)
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

    override suspend fun getMovie(movieId: Int): Resource<Result> {
        return try {
            val response = movieAppService.getMovie(movieId, "en")
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }
            else
                Resource.error("Error!", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

    override suspend fun getMovieImages(movieId: Int): Resource<MovieImages> {
        return try {
            val response = movieAppService.getMovieImages(movieId)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error!", null)
            }
            else
                Resource.error("Error!", null)
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }

}