package io.astronout.core.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import io.astronout.core.data.source.local.LocalDataSource
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.remote.paging.GamesRemoteMediator
import io.astronout.core.data.source.remote.web.ApiClient
import io.astronout.core.domain.model.Game
import io.astronout.core.domain.repository.GamesRepository
import io.astronout.core.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GamesStoryDataStore @Inject constructor(
    private val api: ApiClient,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : GamesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getGames(): Flow<PagingData<GameEntity>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE,),
            remoteMediator = GamesRemoteMediator(api, localDataSource),
            pagingSourceFactory = {
                localDataSource.getAllGames()
            }
        ).flow
    }

    override fun searchGames(query: String) = flow {
        api.searchGames(query).let {
            it.suspendOnSuccess {
                emit(Resource.Success(data.results?.map { game -> Game(game) }.orEmpty()))
            }.suspendOnError {
                emit(Resource.Error(message()))
            }.suspendOnException {
                emit(Resource.Error(message.orEmpty()))
            }
        }
    }.onStart { emit(Resource.Loading) }.flowOn(ioDispatcher)

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}
