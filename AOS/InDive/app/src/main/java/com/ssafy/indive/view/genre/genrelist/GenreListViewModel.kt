package com.ssafy.indive.view.genre.genrelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.indive.model.dto.Song

class GenreListViewModel : ViewModel() {

    private val _genreList = MutableLiveData<MutableList<Song>>()
    val genreList : LiveData<MutableList<Song>>
    get() = _genreList

    fun getGenres(){
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

        _genreList.postValue(songList)
    }


}