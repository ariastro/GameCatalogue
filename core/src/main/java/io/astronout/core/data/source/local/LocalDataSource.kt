package io.astronout.core.data.source.local

import androidx.paging.PagingSource
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.local.entity.RemoteKeys
import io.astronout.core.data.source.local.room.GameDatabase
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getDatabase(): GameDatabase
    fun getAllGames(): PagingSource<Int, GameEntity>
    fun getGameDetail(id: Long): Flow<GameEntity?>
    suspend fun setIsFavorites(isFavorites: Boolean, id: Long)
    fun getAllFavoriteGames(): Flow<List<GameEntity>>
    suspend fun insertGames(games: List<GameEntity>)
    suspend fun insertGame(game: GameEntity)
    suspend fun clearGames()
    suspend fun getRemoteKeys(id: Long): RemoteKeys?
    suspend fun insertRemoteKeys(keys: List<RemoteKeys>)
    suspend fun clearRemoteKeys()

}