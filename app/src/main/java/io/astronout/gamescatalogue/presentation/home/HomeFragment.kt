package io.astronout.gamescatalogue.presentation.home

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kennyc.view.MultiStateView
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.core.utils.showToast
import io.astronout.core.vo.Resource
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentHomeBinding
import io.astronout.gamescatalogue.presentation.home.adapter.GameAdapter

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
            collectLifecycleFlow(viewModel.games) {
                when (it) {
                    is Resource.Loading -> {
                        msvGame.viewState = MultiStateView.ViewState.LOADING
                    }
                    is Resource.Error -> {
                        msvGame.viewState = MultiStateView.ViewState.CONTENT
                        showToast(it.message)
                    }
                    is Resource.Success -> {
                        msvGame.viewState = MultiStateView.ViewState.CONTENT
                        val recyclerViewState = rvGame.layoutManager?.onSaveInstanceState()
                        adapter.submitList(it.data)
                        rvGame.layoutManager?.onRestoreInstanceState(recyclerViewState)
                    }
                }
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvGame.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
    }

}