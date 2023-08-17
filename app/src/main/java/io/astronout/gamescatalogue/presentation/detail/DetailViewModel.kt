package io.astronout.gamescatalogue.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.core.domain.usecase.GameUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val usecase: GameUsecase) : ViewModel() {

    fun getGameDetails(id: Long) = usecase.getGameDetails(id)

    fun setIsFavorites(isFavorites: Boolean, id: Long) {
        viewModelScope.launch {
            usecase.setIsFavorites(isFavorites, id)
        }
    }

}