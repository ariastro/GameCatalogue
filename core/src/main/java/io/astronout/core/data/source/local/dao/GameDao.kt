package io.astronout.core.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.astronout.core.data.source.local.entity.GameEntity

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("SELECT * FROM game")
    fun getAllGames(): PagingSource<Int, GameEntity>

    @Query("SELECT * FROM game WHERE is_favorites = 1")
    fun getAllFavoriteGames(): List<GameEntity>

    @Query("DELETE FROM game")
    fun deleteAllGames()
}