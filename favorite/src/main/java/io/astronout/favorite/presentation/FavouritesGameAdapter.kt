package io.astronout.favorite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.astronout.core.domain.model.Game
import io.astronout.core.utils.ConverterDate
import io.astronout.core.utils.convertDateTo
import io.astronout.core.utils.loadImage
import io.astronout.gamescatalogue.databinding.ItemGameBinding

class FavouritesGameAdapter(private val onItemClicked: (Game) -> Unit): ListAdapter<Game, FavouritesGameAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Game) {
            with(binding) {
                ivGame.loadImage(data.backgroundImage)
                tvGameTitle.text = data.name
                tvReleaseDate.text = data.released.convertDateTo(ConverterDate.FULL_DATE)
                tvGameRate.text = data.rating.toString()
                root.setOnClickListener {
                    onItemClicked(data)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
                return oldItem == newItem
            }
        }
    }

}