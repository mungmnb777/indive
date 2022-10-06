package com.ssafy.indive.view.more.mywallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.blockchain.DonationHistory
import com.ssafy.indive.databinding.ItemDonationBinding


class MyWalletAdapter : ListAdapter<DonationHistory, MyWalletAdapter.MyWalletViewHolder>(diffUtil) {

    inner class MyWalletViewHolder(var binding: ItemDonationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(history: DonationHistory) {

            binding.history = history

//            binding.root.setOnClickListener {
//                historyListener(history)
//            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyWalletAdapter.MyWalletViewHolder {

        val binding = ItemDonationBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )

        return MyWalletViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyWalletViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DonationHistory>() {
            override fun areContentsTheSame(oldItem: DonationHistory, newItem: DonationHistory) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areItemsTheSame(oldItem: DonationHistory, newItem: DonationHistory) =
                oldItem.seq == newItem.seq
        }
    }


}