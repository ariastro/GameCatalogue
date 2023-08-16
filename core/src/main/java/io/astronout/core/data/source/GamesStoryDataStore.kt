package io.astronout.core.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import io.astronout.core.data.source.remote.paging.GamesPagingSource
import io.astronout.core.data.source.remote.web.ApiClient
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GamesStoryDataStore @Inject constructor(
    private val api: ApiClient,
    private val ioDispatcher: CoroutineDispatcher
) : GamesRepository {

    override fun getGames(): Flow<PagingData<Game>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GamesPagingSource(api) }
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
