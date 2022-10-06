package com.ssafy.indive.view.mystudio.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemRankingBinding
import com.ssafy.indive.model.response.DonationRankResponse

class RankingAdapter :
    ListAdapter<DonationRankResponse, RankingAdapter.RankingViewHolder>(diffUtil) {

    inner class RankingViewHolder(var binding: ItemRankingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rank: DonationRankResponse, position: Int) {
            binding.ranking = rank
            binding.num = position + 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingAdapter.RankingViewHolder {

        val binding = ItemRankingBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return RankingViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DonationRankResponse>() {
            override fun areContentsTheSame(
                oldItem: DonationRankResponse,
                newItem: DonationRankResponse
            ) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(
                oldItem: DonationRankResponse,
                newItem: DonationRankResponse
            ) =
                oldItem.memberSeq == newItem.memberSeq
        }
    }
}