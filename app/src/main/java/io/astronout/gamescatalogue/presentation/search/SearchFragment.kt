package io.astronout.gamescatalogue.presentation.search

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.domain.model.Game
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.core.utils.showToast
import io.astronout.core.vo.Resource
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentSearchBinding

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

    override fun initAction() {
        super.initAction()
        with(binding) {
            etSearch.doOnTextChanged { text, _, _, _ ->
                viewModel.setSearchQuery(text.toString())
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        collectLifecycleFlow(viewModel.searchResult) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
    }

}