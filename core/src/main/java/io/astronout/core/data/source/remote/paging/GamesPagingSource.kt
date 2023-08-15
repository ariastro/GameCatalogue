package io.astronout.core.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.astronout.core.data.source.GamesStoryDataStore.Companion.NETWORK_PAGE_SIZE
import io.astronout.core.data.source.remote.web.ApiClient
import retrofit2.HttpException
import java.io.IOException

class GamesPagingSource(
    private val api: ApiClient
) : PagingSource<Int, Unit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Record> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = api.getGames(position, params.loadSize)
            val games = response.data
            val nextKey = if (records.isNullOrEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = records?.map { Record(it) }.orEmpty(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Record>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}