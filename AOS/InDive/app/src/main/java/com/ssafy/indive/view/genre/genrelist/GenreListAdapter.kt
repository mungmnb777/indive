package com.ssafy.indive.view.genre.genrelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemMusicListBinding
import com.ssafy.indive.model.dto.Song


class GenreListAdapter(
    private val playListener: (Song) -> (Unit),
    private val moreListener: (Song) -> (Unit)
) : ListAdapter<Song, GenreListAdapter.GenreListViewHolder>(diffUtil) {
    inner class GenreListViewHolder(val binding: ItemMusicListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(song: Song) {
            binding.song = song

        }

        fun click(song: Song) {
            binding.ivPlay.setOnClickListener {
                playListener(song)
            }
            binding.ivItemMore.setOnClickListener {
                moreListener(song)
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
        val diffUtil = object : DiffUtil.ItemCallback<Song>() {
            override fun areContentsTheSame(oldItem: Song, newItem: Song) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: Song, newItem: Song) =
                oldItem.musicSeq == newItem.musicSeq
        }
    }

}