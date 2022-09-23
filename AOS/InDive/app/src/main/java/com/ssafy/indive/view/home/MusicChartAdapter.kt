package com.ssafy.indive.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemMusicChartBinding
import com.ssafy.indive.model.dto.Music

class MusicChartAdapter(private val playListener : (Music) -> (Unit), private val moreListener : (Music) -> (Unit)) :  ListAdapter<Music, MusicChartAdapter.MusicChartViewHolder>(diffUtil) {

    inner class MusicChartViewHolder(var binding: ItemMusicChartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.song = music
            binding.tvMusicRanking.text = "${position + 1}"

        }

        fun click(music: Music){
            binding.ivPlay.setOnClickListener {
                playListener(music)
            }
            binding.ivItemMore.setOnClickListener {
                moreListener(music)
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
        val diffUtil = object : DiffUtil.ItemCallback<Music>() {
            override fun areContentsTheSame(oldItem: Music, newItem: Music) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: Music, newItem: Music) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }
}