package io.astronout.gamescatalogue.presentation.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.domain.model.Game
import io.astronout.core.utils.ConverterDate
import io.astronout.core.utils.collectLifecycleFlow
import io.astronout.core.utils.convertDateTo
import io.astronout.core.utils.getDrawableCompat
import io.astronout.core.utils.loadImage
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentGameDetailBinding
import timber.log.Timber

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

    override fun initData() {
        super.initData()
        collectLifecycleFlow(viewModel.getGameDetails(args.game.id)) {
            Timber.d("checkData: $it")
            showGameDetails(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showGameDetails(game: Game) {
        with(binding) {
            toolbar.title = game.name
            tvTitle.text = game.name
            tvReleaseDate.text = "Released: ${game.released.convertDateTo(ConverterDate.FULL_DATE)}"
            tvRating.text = "${game.rating}/5"
            ivGame.loadImage(game.backgroundImage)
            ivFavorites.isActivated = game.isFavorites
            tvGenre.text = game.genres.joinToString()
            toggleFavorites()
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

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

}