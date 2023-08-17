package io.astronout.gamescatalogue.presentation.favourite

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentFavouritesBinding
import io.astronout.gamescatalogue.presentation.search.SearchFragmentDirections
import io.astronout.gamescatalogue.presentation.search.SearchGameAdapter

class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val binding: FragmentFavouritesBinding by viewBinding()
    private val viewModel: FavouritesViewModel by viewModels()
    private val adapter: SearchGameAdapter by lazy {
        SearchGameAdapter {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(it))
        }
    }

    override fun initData() {
        super.initData()
        collectLifecycleFlow(viewModel.favoriteGames) {
            adapter.submitList(it)
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvGame.adapter = adapter
        }
    }

}