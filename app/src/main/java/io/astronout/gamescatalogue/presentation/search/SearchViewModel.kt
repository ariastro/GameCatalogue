package io.astronout.gamescatalogue.presentation.search

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.core.domain.usecase.GameUsecase
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val usecase: GameUsecase) : ViewModel() {

    fun searchGames(query: String) = usecase.searchGames(query)

}