package com.ssafy.indive.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemBannerBinding
import com.ssafy.indive.model.dto.Banner


class BannerAdapter(private val bannerList : MutableList<Banner>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(val binding : ItemBannerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(banner: Banner){
            binding.ivBanner.setImageResource(banner.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
       holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size


}