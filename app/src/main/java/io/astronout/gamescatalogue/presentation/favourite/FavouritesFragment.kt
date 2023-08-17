package io.astronout.gamescatalogue.presentation.favourite

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentFavouritesBinding

class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val binding: FragmentFavouritesBinding by viewBinding()
    private val viewModel: FavouritesViewModel by viewModels()
    private val adapter: FavouritesGameAdapter by lazy {
        FavouritesGameAdapter {
            findNavController().navigate(FavouritesFragmentDirections.actionFavouritesFragmentToGameDetailFragment(it))
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