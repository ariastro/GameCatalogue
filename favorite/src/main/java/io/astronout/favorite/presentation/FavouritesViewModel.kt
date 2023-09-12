package io.astronout.favorite.presentation

import androidx.lifecycle.ViewModel
import io.astronout.core.domain.usecase.GameUsecase
import javax.inject.Inject

class FavouritesViewModel @Inject constructor(usecase: GameUsecase) : ViewModel() {

    val favoriteGames = usecase.getAllFavoritesGames()

}