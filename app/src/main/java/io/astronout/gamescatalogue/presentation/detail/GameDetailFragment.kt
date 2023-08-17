package io.astronout.gamescatalogue.presentation.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.ConverterDate
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.core.utils.convertDateTo
import io.astronout.core.utils.getDrawableCompat
import io.astronout.core.utils.loadImage
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentGameDetailBinding

class GameDetailFragment : BaseFragment(R.layout.fragment_game_detail) {

    private val binding: FragmentGameDetailBinding by viewBinding()
    private val viewModel: DetailViewModel by viewModels()
    private val args: GameDetailFragmentArgs by navArgs()

    override fun initUI() {
        super.initUI()
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        super.initData()
        with(binding) {
            collectLifecycleFlow(viewModel.getGameDetails(args.game.id)) {
                if (it != null) {
                    toolbar.title = it.name
                    tvTitle.text = it.name
                    tvReleaseDate.text =
                        "Released: ${it.released.convertDateTo(ConverterDate.FULL_DATE)}"
                    tvRating.text = "${it.rating}/5"
                    ivGame.loadImage(it.backgroundImage)
                    binding.ivFavorites.isActivated = it.isFavorites
                    toggleFavorites()
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            cvFavorite.setOnClickListener {
                ivFavorites.isActivated = !ivFavorites.isActivated
                viewModel.setIsFavorites(ivFavorites.isActivated, args.game.id)
                toggleFavorites()
            }
        }
    }

    private fun toggleFavorites() {
        with(binding.ivFavorites) {
            setImageDrawable(getDrawableCompat(if (isActivated) R.drawable.ic_favourite_on else R.drawable.ic_favourite))
        }
    }

}