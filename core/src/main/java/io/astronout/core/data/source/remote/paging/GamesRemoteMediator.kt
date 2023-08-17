package io.astronout.core.data.source.remote.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.skydoves.sandwich.suspendOnSuccess
import io.astronout.core.data.source.local.LocalDataSource
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.local.entity.RemoteKeys
import io.astronout.core.data.source.remote.web.ApiClient

@ExperimentalPagingApi
class GamesRemoteMediator(
    private val api: ApiClient,
    private val localDataSource: LocalDataSource
) : RemoteMediator<Int, GameEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GameEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        try {
            val responseData = api.getGames(page, state.config.pageSize)
            var endOfPaginationReached = false
            responseData.suspendOnSuccess {
                val games = response.body()?.results
                endOfPaginationReached = games.isNullOrEmpty()
                localDataSource.getDatabase().withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        localDataSource.clearRemoteKeys()
                        localDataSource.clearGames()
                    }

                    val prevKey = if (page == 1) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = games?.map {
                        RemoteKeys(id = it.id ?: 0, prevKey = prevKey, nextKey = nextKey)
                    }

                    keys?.let {
                        localDataSource.insertRemoteKeys(it)
                    }
                    games?.map { GameEntity(it) }?.let {
                        localDataSource.insertGames(it)
                    }
                }
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeys(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeys(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, GameEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeys(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}