package com.ssafy.indive.view.mystudio.addsong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.databinding.ItemGenreBinding

class GenreAdapter(): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    var genreList = mutableListOf<String>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolder(var binding: ItemGenreBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(genre: String){
            binding.apply {
                btnGenre.text = genre
                btnGenre.setOnClickListener {
                    onClickGenreItem.onClick(it, adapterPosition, genre)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemGenreBinding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemGenreBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    interface OnClickGenreListener{
        fun onClick(view: View, position: Int, genre: String)
    }
    lateinit var onClickGenreItem: OnClickGenreListener
}