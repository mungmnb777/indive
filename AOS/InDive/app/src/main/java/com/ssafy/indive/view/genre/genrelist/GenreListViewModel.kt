package com.ssafy.indive.view.genre.genrelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.indive.model.dto.Music

class GenreListViewModel : ViewModel() {

    private val _genreList = MutableLiveData<MutableList<Music>>()
    val genreList : LiveData<MutableList<Music>>
    get() = _genreList

//    fun getGenres(){
//        val songList = mutableListOf(
//            Music("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"),
//            Music("1", "2", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "3", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "4", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "5", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "6", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "7", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "8", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//        )
//
//        _genreList.postValue(songList)
//    }


}