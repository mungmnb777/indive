package com.ssafy.indive.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.indive.R
import com.ssafy.indive.binding.RecyclerBinding.bindMusicImage
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import com.ssafy.indive.utils.*
import com.ssafy.indive.view.genre.genrelist.GenreListAdapter
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.home.RecentMusicAdapter
import com.ssafy.indive.view.home.search.SearchArtistAdapter
import com.ssafy.indive.view.songdetail.ReplyAdapter
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

                is GenreListAdapter -> {
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<MusicDetailResponse>)
                }

                is SearchArtistAdapter -> {
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<MusicDetailResponse>)
                }

                else -> {
                    Log.d("d102", "else")
                }
            }
        } else if (result is Result.Empty) {
            (view.adapter as ListAdapter<Any, *>).submitList(emptyList())
        }


    }

    @BindingAdapter("setImageCircle")
    @JvmStatic
    fun CircleImageView.bindImage(seq : Long) {
        Glide.with(this.context).load("${MEMBER_HEADER}$seq${MEMBER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image

            ).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
        Log.d("d102", "res: ${MEMBER_HEADER}$seq${MEMBER_FOOTER}")
    }

    @BindingAdapter("bindMusicImage")
    @JvmStatic
    fun ImageView.bindMusicImage(seq: Long) {
        Glide.with(this.context).load("${COVER_HEADER}$seq${COVER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image
            ).into(this)

    }

    @BindingAdapter("bindBackImage")
    @JvmStatic
    fun ImageView.bindBackImage(seq: Long) {
        Glide.with(this.context).load("${BACKGROUND_HEADER}$seq${BACKGROUND_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)

    }


}
