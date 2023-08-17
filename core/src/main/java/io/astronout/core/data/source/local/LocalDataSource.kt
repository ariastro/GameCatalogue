package io.astronout.core.data.source.local

import androidx.paging.PagingSource
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.local.entity.RemoteKeys
import io.astronout.core.data.source.local.room.GameDatabase

interface LocalDataSource {
    fun getDatabase(): GameDatabase
    fun getAllGames(): PagingSource<Int, GameEntity>
    fun getAllFavoriteGames(): List<GameEntity>
    suspend fun insertGames(games: List<GameEntity>)
    suspend fun clearGames()
    suspend fun getRemoteKeys(id: Int): RemoteKeys?
    suspend fun insertRemoteKeys(keys: List<RemoteKeys>)
    suspend fun clearRemoteKeys()

}