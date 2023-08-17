package io.astronout.gamescatalogue.presentation.search

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.domain.model.Game
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.core.utils.showToast
import io.astronout.core.utils.textChanges
import io.astronout.core.vo.Resource
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentSearchBinding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModels()

    private val adapter: SearchGameAdapter by lazy {
        SearchGameAdapter {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(it))
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvGame.adapter = adapter
        }
    }

    @OptIn(FlowPreview::class)
    override fun initAction() {
        super.initAction()
        with(binding) {
            etSearch.textChanges()
                .debounce(600)
                .onEach {
                    if (!it.isNullOrEmpty()) {
                        searchGames(it.toString())
                    } else {
                        adapter.submitList(emptyList())
                    }
                }
                .launchIn(lifecycleScope)
        }
    }

    private fun searchGames(query: String) {
        collectLifecycleFlow(viewModel.searchGames(query)) {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {
                    showToast(it.message)
                }
                is Resource.Success -> {
                    onSearchGamesSuccess(it.data)
                }
            }
        }
    }

    private fun onSearchGamesSuccess(data: List<Game>) {
        adapter.submitList(data)
    }

}