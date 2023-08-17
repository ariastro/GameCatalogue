package io.astronout.core.data.source.local

import androidx.paging.PagingSource
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.local.entity.RemoteKeys
import io.astronout.core.data.source.local.room.GameDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val appDatabase: GameDatabase
) : LocalDataSource {

    override fun getDatabase(): GameDatabase = appDatabase

    override fun getAllGames(): PagingSource<Int, GameEntity> {
        return appDatabase.gameDao().getAllGames()
    }

    override suspend fun setIsFavorites(isFavorites: Boolean, id: Long) {
        return appDatabase.gameDao().setIsFavorites(isFavorites, id)
    }

    override fun getAllFavoriteGames(): Flow<List<GameEntity>> {
        return appDatabase.gameDao().getAllFavoriteGames()
    }

    override suspend fun insertGames(games: List<GameEntity>) {
        appDatabase.gameDao().insertGames(games)
    }

    override suspend fun clearGames() {
        appDatabase.gameDao().deleteAllGames()
    }

    override suspend fun getRemoteKeys(id: Long): RemoteKeys? {
        return appDatabase.remoteKeysDao().getRemoteKeysId(id)
    }

    override suspend fun insertRemoteKeys(keys: List<RemoteKeys>) {
        appDatabase.remoteKeysDao().insertAll(keys)
    }

    override suspend fun clearRemoteKeys() {
        appDatabase.remoteKeysDao().clearRemoteKeys()
    }
}