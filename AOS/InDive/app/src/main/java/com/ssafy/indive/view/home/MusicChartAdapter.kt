package com.ssafy.indive.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemMusicChartBinding
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse

class MusicChartAdapter(private val playListener : (MusicDetailResponse) -> (Unit), private val moreListener : (MusicDetailResponse) -> (Unit)) :  ListAdapter<MusicDetailResponse, MusicChartAdapter.MusicChartViewHolder>(diffUtil) {

    inner class MusicChartViewHolder(var binding: ItemMusicChartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: MusicDetailResponse) {
            binding.song = music
            binding.tvMusicRanking.text = "${position + 1}"

        }

        fun click(music: MusicDetailResponse){
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
        val diffUtil = object : DiffUtil.ItemCallback<MusicDetailResponse>() {
            override fun areContentsTheSame(oldItem: MusicDetailResponse, newItem: MusicDetailResponse) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: MusicDetailResponse, newItem: MusicDetailResponse) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }
}