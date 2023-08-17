package io.astronout.core.data.source

import androidx.paging.PagingData
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    fun getGames(): Flow<PagingData<GameEntity>>
    fun searchGames(query: String): Flow<Resource<List<Game>>>
}
