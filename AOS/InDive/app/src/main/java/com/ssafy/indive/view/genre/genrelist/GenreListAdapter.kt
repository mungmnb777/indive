package com.ssafy.indive.view.genre.genrelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemMusicListBinding
import com.ssafy.indive.model.dto.Music


class GenreListAdapter(
    private val playListener: (Music) -> (Unit),
    private val moreListener: (Music) -> (Unit)
) : ListAdapter<Music, GenreListAdapter.GenreListViewHolder>(diffUtil) {
    inner class GenreListViewHolder(val binding: ItemMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.song = music

        }

        fun click(music: Music) {
            binding.ivPlay.setOnClickListener {
                playListener(music)
            }
            binding.ivItemMore.setOnClickListener {
                moreListener(music)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreListViewHolder {
        val binding =
            ItemMusicListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreListViewHolder, position: Int) {
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