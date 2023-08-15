package io.astronout.core.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import io.astronout.core.data.source.remote.web.ApiClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GamesStoryDataStore @Inject constructor(
    private val api: ApiClient,
    private val ioDispatcher: CoroutineDispatcher
) : GamesRepository {

    override fun getGames(page: Int, pageSize: Int): Flow<PagingData<Unit>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RecordPagingSource(api, date, scheduleIds) }
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}
