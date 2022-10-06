package com.ssafy.indive.view.more.box

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemBoxListBinding

class BoxAdapter(): ListAdapter<String, BoxAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(var binding: ItemBoxListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tokenURI: String) {
            binding.tokenURI = tokenURI
        }
//
//        fun click(music: MusicDetailResponse) {
//            binding.ivPlay.setOnClickListener {
//                playListener(music)
//            }
//            binding.ivItemMore.setOnClickListener {
//                moreListener(music)
//            }
//
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxAdapter.ViewHolder {

        val binding = ItemBoxListBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: BoxAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
//        holder.click(getItem(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areContentsTheSame(
                oldItem: String,
                newItem: String
            ) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(
                oldItem: String,
                newItem: String
            ) =
                oldItem == newItem
        }
    }
}