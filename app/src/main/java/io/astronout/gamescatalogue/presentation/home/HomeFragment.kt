package io.astronout.gamescatalogue.presentation.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.collectLatestLifecycleFlow
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentHomeBinding
import io.astronout.gamescatalogue.presentation.home.adapter.GameAdapter
import io.astronout.gamescatalogue.presentation.home.adapter.LoadingStateAdapter

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    private val adapter: GameAdapter by lazy {
        GameAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGameDetailFragment(it))
        }
    }

    override fun initObserver() {
        super.initObserver()
        with(binding) {
            collectLatestLifecycleFlow(viewModel.games) {
                val recyclerViewState = rvGame.layoutManager?.onSaveInstanceState()
                adapter.submitData(lifecycle, it)
                rvGame.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            adapter.addLoadStateListener { loadState ->
                if ((loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) || loadState.source.refresh is LoadState.Error) {
//                    msvStories.showEmptyLayout()
                } else {
//                    msvStories.showDefaultLayout()
                }
            }
            runCatching {
                rvGame.adapter = adapter.withLoadStateFooter(
                    footer = LoadingStateAdapter {
                        adapter.retry()
                    }
                )
            }
        }
    }
}