package io.astronout.core.domain.usecase

import androidx.paging.PagingData
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface GameUsecase {

    fun getGames(): Flow<PagingData<Game>>

    fun searchGames(query: String): Flow<Resource<List<Game>>>

    suspend fun setIsFavorites(isFavorites: Boolean, id: Long)

}