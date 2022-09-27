package com.ssafy.indive.binding

import android.media.Image
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.indive.R
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.*
import com.ssafy.indive.view.genre.genrelist.GenreListAdapter
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.home.RecentMusicAdapter
import com.ssafy.indive.view.songdetail.CommentAdapter
import de.hdodenhof.circleimageview.CircleImageView

object RecyclerBinding {

    @BindingAdapter("submitList")
    @JvmStatic
    fun bindSubmitList(view: RecyclerView, result: Result<*>?) {
        Log.d("d102", "res: $result")
        Log.d("d102", "view: ${view.adapter}")

        if (result is Result.Success) {
            when (view.adapter) {

                is RecentMusicAdapter -> {
                    Log.d("d102", "RecentMusicAdapter:")
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<MusicDetailResponse>)
                }
                is MusicChartAdapter -> {
                    Log.d("d102", "MusicChartAdapter")
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<MusicDetailResponse>)
                }

//                is CommentAdapter -> {
//                    Log.d("d102", "CommentAdapter")
//                    (view.adapter as ListAdapter<Any, *>).submitList(result)
//                }
//
//                is GenreListAdapter -> {
//                    Log.d("d102", "CommentAdapter")
//                    (view.adapter as ListAdapter<Any, *>).submitList(result)
//                }

                else -> {
                    Log.d("d102", "else")
                }
            }
        } else if (result is Result.Empty) {
            (view.adapter as ListAdapter<Any, *>).submitList(emptyList())
        }


    }

    @BindingAdapter("setImage")
    @JvmStatic
    fun bindImage(view: CircleImageView, img: Int) {
        Log.d("d102", "res: $img")
        view.setImageResource(img)
    }


    @BindingAdapter("bindMusicImage")
    @JvmStatic
    fun ImageView.bindMusicImage(seq: Long) {
        Glide.with(this.context).load("${COVER_HEADER}$seq${COVER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image
            ).into(this)

    }

    @BindingAdapter("bindMemberImage")
    @JvmStatic
    fun ImageView.bindMemberImage(seq: Long) {
        Glide.with(this.context).load("${MEMBER_HEADER}$seq${MEMBER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.member_default_image
            ).into(this)

    }


}
