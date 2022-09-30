package com.ssafy.indive.view.home


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemRecentMusicBinding
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.TAG

class RecentMusicAdapter(private val playListener : (MusicDetailResponse) -> (Unit)) : ListAdapter<MusicDetailResponse, RecentMusicAdapter.RecentMusicViewHolder>(diffUtil) {


    inner class RecentMusicViewHolder(var binding: ItemRecentMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: MusicDetailResponse) {

            binding.music = music

            binding.root.setOnClickListener {
                playListener(music)
            }

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
        val diffUtil = object : DiffUtil.ItemCallback<MusicDetailResponse>() {
            override fun areContentsTheSame(oldItem: MusicDetailResponse, newItem: MusicDetailResponse) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: MusicDetailResponse, newItem: MusicDetailResponse) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }


}