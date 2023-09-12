package io.astronout.favorite.presentation

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kennyc.view.MultiStateView
import dagger.hilt.android.EntryPointAccessors
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.di.FavouritesModuleDependencies
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.favorite.R
import io.astronout.favorite.databinding.FragmentFavouritesBinding
import io.astronout.favorite.presentation.di.DaggerFavouritesComponent
import javax.inject.Inject

class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val binding: FragmentFavouritesBinding by viewBinding()

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavouritesViewModel by viewModels { factory }
    private val adapter: FavouritesGameAdapter by lazy {
        FavouritesGameAdapter {
            findNavController().navigate(
                FavouritesFragmentDirections.actionFavouritesFragmentToGameDetailFragment(
                    it
                )
            )
        }
    }

    override fun initData() {
        super.initData()
        collectLifecycleFlow(viewModel.favoriteGames) {
            with(binding) {
                if (it.isNotEmpty()) {
                    binding.msvFavorites.viewState = MultiStateView.ViewState.CONTENT
                    adapter.submitList(it)
                } else {
                    binding.msvFavorites.viewState = MultiStateView.ViewState.EMPTY
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initCoreDependentInjection()
    }

    private fun initCoreDependentInjection() {
        DaggerFavouritesComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavouritesModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
    }

}