package io.astronout.gamescatalogue.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.astronout.core.domain.usecase.GameUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val usecase: GameUsecase) : ViewModel() {

    private val queryChannel = MutableStateFlow("")

    fun setSearchQuery(search: String) {
        viewModelScope.launch {
            queryChannel.value = search
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            usecase.searchGames(it)
        }

}