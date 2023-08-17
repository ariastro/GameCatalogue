package io.astronout.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import io.astronout.core.data.source.local.dao.GameDao
import io.astronout.core.data.source.local.dao.RemoteKeysDao
import io.astronout.core.data.source.local.entity.GameEntity
import io.astronout.core.data.source.local.entity.RemoteKeys

@Database(
    entities = [GameEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}