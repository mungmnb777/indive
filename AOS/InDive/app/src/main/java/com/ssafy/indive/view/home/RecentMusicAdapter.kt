package com.ssafy.indive.view.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemRecentMusicBinding
import com.ssafy.indive.model.dto.Music

class RecentMusicAdapter : ListAdapter<Music, RecentMusicAdapter.RecentMusicViewHolder>(diffUtil) {


    inner class RecentMusicViewHolder(var binding: ItemRecentMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.song = music


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMusicViewHolder {

        val binding = ItemRecentMusicBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return RecentMusicViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecentMusicViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Music>() {
            override fun areContentsTheSame(oldItem: Music, newItem: Music) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: Music, newItem: Music) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }


}