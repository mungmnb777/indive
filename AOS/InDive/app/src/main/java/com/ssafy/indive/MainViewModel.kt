package com.ssafy.indive

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _nowPlaying = MutableLiveData<Boolean>()
    val nowPlaying : LiveData<Boolean>
        get() = _nowPlaying

    init {
        _nowPlaying.postValue(false)
    }

    fun play(){
        _nowPlaying.postValue(true)
    }
}