package io.astronout.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import io.astronout.core.data.source.GamesRepository
import io.astronout.core.data.source.local.LocalDataSource
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameInteractor @Inject constructor(private val repo: GamesRepository, private val localDataSource: LocalDataSource) : GameUsecase {

    override fun getGames(): Flow<PagingData<Game>> {
        return repo.getGames().map { data ->
            data.map { Game(it) }
        }
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> {
        return repo.searchGames(query)
    }

    override suspend fun setIsFavorites(isFavorites: Boolean, id: Long) {
        localDataSource.setIsFavorites(isFavorites, id)
    }

    override fun getAllFavoritesGames(): Flow<List<Game>> {
        return localDataSource.getAllFavoriteGames().map { data ->
            data.map { Game(it) }
        }
    }

    override fun getGameDetails(id: Long): Flow<Game?> {
        return localDataSource.getGameDetail(id).map {
            Game(it)
        }
    }

}