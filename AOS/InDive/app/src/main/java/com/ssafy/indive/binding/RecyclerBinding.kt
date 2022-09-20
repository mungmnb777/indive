package com.ssafy.indive.binding

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.home.RecentMusicAdapter

object RecyclerBinding {
    @BindingAdapter("submitList")
    @JvmStatic
    fun bindSubmitList(view: RecyclerView, result: List<*>?) {
        Log.d("d102", "res: $result")
        Log.d("d102", "view: ${view.adapter}")
        when(view.adapter){

            is RecentMusicAdapter ->{
                Log.d("d102", "RecentMusicAdapter:")
                (view.adapter as ListAdapter<Any, *>).submitList(result)
            }
            is MusicChartAdapter ->{
                Log.d("d102", "MusicChartAdapter")
                (view.adapter as ListAdapter<Any, *>).submitList(result)
            }
            else -> {
                Log.d("d102", "else")
            }
        }

    }
}