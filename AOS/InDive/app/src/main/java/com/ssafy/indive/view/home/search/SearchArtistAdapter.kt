package com.ssafy.indive.view.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemSearchArtistBinding
import com.ssafy.indive.model.dto.SearchArtist
import com.ssafy.indive.model.response.MusicDetailResponse

class SearchArtistAdapter(val clickListener: (SearchArtist) -> (Unit)) :
    ListAdapter<SearchArtist, SearchArtistAdapter.SearchViewHolder>(diffUtil) {

    inner class SearchViewHolder(val binding: ItemSearchArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(searchArtist: SearchArtist) {
            binding.searchArtist = searchArtist
        }

        fun click(searchArtist: SearchArtist) {
            binding.root.setOnClickListener {
                clickListener(searchArtist)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchArtistAdapter.SearchViewHolder {
        val binding =
            ItemSearchArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchArtistAdapter.SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.click(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchArtist>() {
            override fun areItemsTheSame(
                oldItem: SearchArtist,
                newItem: SearchArtist
            ): Boolean =
                oldItem.hashCode() == newItem.hashCode()


            override fun areContentsTheSame(
                oldItem: SearchArtist,
                newItem: SearchArtist
            ): Boolean =
                oldItem.memberSeq == newItem.memberSeq

        }
    }

}