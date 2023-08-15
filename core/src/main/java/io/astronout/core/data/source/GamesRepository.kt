package io.astronout.core.data.source

import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    fun getGames(page: Int, pageSize: Int): Flow<Resource<Unit>>
}
