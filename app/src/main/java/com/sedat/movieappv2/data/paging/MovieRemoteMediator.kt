package com.sedat.movieappv2.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sedat.movieappv2.data.local.AppDatabase
import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.data.local.entity.RemoteKeyEntity
import com.sedat.movieappv2.data.remote.MovieAppService
import com.sedat.movieappv2.util.SharedPrefsLanguage
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val movieAppService: MovieAppService,
    private val db: AppDatabase,
    private val sharedPrefsLanguage: SharedPrefsLanguage
): RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val language = sharedPrefsLanguage.getLanguage()
            val response = movieAppService.getMovies(page, language)

            val isEndOfList = response.results.isEmpty()

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieDao.deleteMovies()
                    db.remoteKeyDao.deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKeyEntity(
                        it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                db.remoteKeyDao.insertAll(keys)
                db.movieDao.saveMovies(response.results.map { it.toMovieEntity() })
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
            }
            else -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { movieId ->
                db.remoteKeyDao.remoteKeysCharacterId(movieId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> db.remoteKeyDao.remoteKeysCharacterId(movie.movieId) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieEntity>): RemoteKeyEntity? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> db.remoteKeyDao.remoteKeysCharacterId(movie.movieId) }
    }

}