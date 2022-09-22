package com.ssafy.indive.binding

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.view.genre.genrelist.GenreListAdapter
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.home.RecentMusicAdapter
import com.ssafy.indive.view.songdetail.CommentAdapter
import de.hdodenhof.circleimageview.CircleImageView

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

            is CommentAdapter -> {
                Log.d("d102", "CommentAdapter")
                (view.adapter as ListAdapter<Any, *>).submitList(result)
            }

            is GenreListAdapter -> {
                Log.d("d102", "CommentAdapter")
                (view.adapter as ListAdapter<Any, *>).submitList(result)
            }

            else -> {
                Log.d("d102", "else")
            }
        }

    }

    @BindingAdapter("setImage")
    @JvmStatic
    fun bindImage(view: CircleImageView, img : Int) {
        Log.d("d102", "res: $img")
        view.setImageResource(img)
        }

    }
