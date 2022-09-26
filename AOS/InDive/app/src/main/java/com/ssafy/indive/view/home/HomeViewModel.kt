package com.ssafy.indive.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {

    private val _musicList: MutableStateFlow<Result<List<Music>>> =
        MutableStateFlow(Result.Unintialized)
    val musicList get() = _musicList.asStateFlow()

    private val _str: MutableStateFlow<String> = MutableStateFlow("")
    val str get() = _str.asStateFlow()

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    private val _recentSongList = MutableLiveData<MutableList<Music>>()
    val recentMusicList: LiveData<MutableList<Music>>
        get() = _recentSongList

    private val _popularSongList = MutableLiveData<MutableList<Music>>()
    val popularMusicList: LiveData<MutableList<Music>>
        get() = _popularSongList


    fun getMusics(title: String?, artistName: String?, sort: String?, genre: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(title, artistName, sort, genre).collectLatest {
                _musicList.value = it
                _successMsgEvent.postValue("가져오기 성공")
            }
        }
    }


    fun initRecentSongList() {

    }

    fun initPopularSongList() {

        val popularList = mutableListOf(Music(0,"제목","","","","","","","",null,null,"가수"))
        _popularSongList.postValue(popularList)
    }


}