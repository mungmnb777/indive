package com.ssafy.indive.view.player

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.indive.databinding.ItemPlayListBinding
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.dto.PlayListMusic


class PlayListAdapter(private val callback: (Int) -> Unit) :
    ListAdapter<PlayListMusic, PlayListAdapter.ViewHolder>(
        diffUtil
    ) {

    inner class ViewHolder(val binding: ItemPlayListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayListMusic) {

            binding.tvItemTitle.text = item.track
            binding.tvItemArtist.text = item.artist

            Glide.with(binding.ivItemImg.context)
                .load(item.coverUrl)
                .into(binding.ivItemImg)

            if (item.isPlaying) {
                itemView.setBackgroundColor(Color.GRAY)
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            itemView.setOnClickListener {
                callback(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPlayListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
//        currentList[position].also { musicModel ->
//            holder.bind(musicModel)
//        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PlayListMusic>() {
            override fun areItemsTheSame(oldItem: PlayListMusic, newItem: PlayListMusic): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PlayListMusic,
                newItem: PlayListMusic
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}