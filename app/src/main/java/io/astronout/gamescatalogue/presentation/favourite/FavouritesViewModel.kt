package io.astronout.gamescatalogue.presentation.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.core.domain.usecase.GameUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(usecase: GameUsecase) : ViewModel() {

    val favoriteGames = usecase.getAllFavoritesGames()

}