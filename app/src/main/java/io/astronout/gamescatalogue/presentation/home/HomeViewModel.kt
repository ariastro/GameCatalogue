package io.astronout.gamescatalogue.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.core.domain.usecase.GameUsecase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(usecase: GameUsecase) : ViewModel() {

    val games = usecase.getAllGames()

}