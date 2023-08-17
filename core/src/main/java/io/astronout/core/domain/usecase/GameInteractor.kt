package io.astronout.core.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import io.astronout.core.data.source.GamesRepository
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameInteractor @Inject constructor(private val repo: GamesRepository) : GameUsecase {

    override fun getGames(): Flow<PagingData<Game>> {
        return repo.getGames().map { data ->
            data.map { Game(it) }
        }
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> {
        return repo.searchGames(query)
    }

}