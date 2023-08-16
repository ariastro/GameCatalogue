package io.astronout.core.domain.usecase

import androidx.paging.PagingData
import io.astronout.core.data.source.GamesRepository
import io.astronout.core.domain.model.Game
import io.astronout.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val repo: GamesRepository) : GameUsecase {

    override fun getGames(): Flow<PagingData<Game>> {
        return repo.getGames()
    }

    override fun searchGames(query: String): Flow<Resource<List<Game>>> {
        return repo.searchGames(query)
    }

}