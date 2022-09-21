package com.ssafy.indive.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.indive.model.dto.Song

class HomeViewModel : ViewModel() {

    private val _recentSongList = MutableLiveData<MutableList<Song>>()
    val recentSongList: LiveData<MutableList<Song>>
        get() = _recentSongList

    private val _popularSongList = MutableLiveData<MutableList<Song>>()
    val popularSongList: LiveData<MutableList<Song>>
        get() = _popularSongList




    fun initRecentSongList(){
        val songList = mutableListOf(
            Song("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"),
            Song("1", "2", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "3", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "4", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "5", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "6", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "7", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "8", "1", "1", "1", "1", "1", "1", "1", "1", "1")
        )

        _recentSongList.postValue(songList)
    }

    fun initPopularSongList(){
        val songList = mutableListOf(
            Song("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"),
            Song("1", "2", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "3", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "4", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "5", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "6", "1", "1", "1", "1", "1", "1", "1", "1", "1")
            ,Song("1", "7", "1", "1", "1", "1", "1", "1", "1", "1", "1")
        )

        _popularSongList.postValue(songList)
    }



}