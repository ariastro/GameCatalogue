package io.astronout.gamescatalogue.presentation.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import io.astronout.core.base.BaseFragment
import io.astronout.core.binding.viewBinding
import io.astronout.core.utils.ConverterDate
import io.astronout.core.utils.convertDateTo
import io.astronout.core.utils.loadImage
import io.astronout.gamescatalogue.R
import io.astronout.gamescatalogue.databinding.FragmentGameDetailBinding

class GameDetailFragment : BaseFragment(R.layout.fragment_game_detail) {

    private val binding: FragmentGameDetailBinding by viewBinding()
    private val args: GameDetailFragmentArgs by navArgs()

    override fun initUI() {
        super.initUI()
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        super.initData()
        with(binding) {
            toolbar.title = args.game.name
            tvTitle.text = args.game.name
            tvReleaseDate.text = "Released: ${args.game.released.convertDateTo(ConverterDate.FULL_DATE)}"
            tvRating.text = "${args.game.rating}/5"
            ivGame.loadImage(args.game.backgroundImage)
        }
    }

}