package io.astronout.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.astronout.core.domain.usecase.GameUsecase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val usecase: GameUsecase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> {
                FavouritesViewModel(usecase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}