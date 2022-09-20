package com.ssafy.indive.view.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemRecentMusicBinding
import com.ssafy.indive.model.dto.Song

class RecentMusicAdapter : ListAdapter<Song, RecentMusicAdapter.RecentMusicViewHolder>(diffUtil) {


    inner class RecentMusicViewHolder(var binding: ItemRecentMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song


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
        val diffUtil = object : DiffUtil.ItemCallback<Song>() {
            override fun areContentsTheSame(oldItem: Song, newItem: Song) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: Song, newItem: Song) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }


}