package com.ssafy.indive.binding

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.indive.blockchain.DonationHistory
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.setArtist
import com.ssafy.indive.view.genre.genrelist.GenreListAdapter
import com.ssafy.indive.view.home.MusicChartAdapter
import com.ssafy.indive.view.home.RecentMusicAdapter
import com.ssafy.indive.view.home.search.SearchArtistAdapter
import com.ssafy.indive.view.more.box.BoxAdapter
import com.ssafy.indive.view.more.mywallet.MyWalletAdapter

object RecyclerBinding {

    @BindingAdapter("submitList")
    @JvmStatic
    fun bindSubmitList(view: RecyclerView, result: Result<*>?) {

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
                    val artistList = setArtist(result.data as List<MusicDetailResponse>)

                    (view.adapter as ListAdapter<Any, *>).submitList(artistList)
                }

                is MyWalletAdapter -> {
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<DonationHistory>)
                }

                is BoxAdapter -> {
                    (view.adapter as ListAdapter<Any, *>).submitList(result.data as List<String>)
                }

                else -> {
                    Log.d("d102", "else")
                }
            }
        } else if (result is Result.Empty) {
            (view.adapter as ListAdapter<Any, *>).submitList(emptyList())
        }


    }



}
