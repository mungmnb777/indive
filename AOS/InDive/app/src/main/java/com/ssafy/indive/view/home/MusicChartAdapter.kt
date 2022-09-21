package com.ssafy.indive.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemMusicChartBinding
import com.ssafy.indive.model.dto.Song

class MusicChartAdapter(private val playListener : (Song) -> (Unit)) :  ListAdapter<Song, MusicChartAdapter.MusicChartViewHolder>(diffUtil) {

    inner class MusicChartViewHolder(var binding: ItemMusicChartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song
            binding.tvMusicRanking.text = "${position + 1}"

        }

        fun click(song: Song){
            binding.ivPlay.setOnClickListener {
                playListener(song)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicChartViewHolder {

        val binding = ItemMusicChartBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return MusicChartViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MusicChartViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.click(getItem(position))
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