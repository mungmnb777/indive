package com.ssafy.indive.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.dto.Music
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.Result
import com.ssafy.indive.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {

    private val _musicList : MutableStateFlow<Result<List<Music>>> = MutableStateFlow(Result.Unintialized)
    val musicList get() = _musicList

    private val _successMsgEvent = SingleLiveEvent<String>()
    val successMsgEvent get() = _successMsgEvent

    private val _recentSongList = MutableLiveData<MutableList<Music>>()
    val recentMusicList: LiveData<MutableList<Music>>
        get() = _recentSongList

    private val _popularSongList = MutableLiveData<MutableList<Music>>()
    val popularMusicList: LiveData<MutableList<Music>>
        get() = _popularSongList


    fun getMusics(title: String?, artistName: String?, sort: String?, genre: String?){
        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusics(title, artistName, sort, genre).collectLatest {
                _musicList.value = it
                _successMsgEvent.postValue("가져오기 성공")
            }
        }
    }


//    fun initRecentSongList(){
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
//        _recentSongList.postValue(songList)
//    }
//
//    fun initPopularSongList(){
//        val songList = mutableListOf(
//            Music("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"),
//            Music("1", "2", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "3", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "4", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "5", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "6", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//            ,Music("1", "7", "1", "1", "1", "1", "1", "1", "1", "1", "1")
//        )
//
//        _popularSongList.postValue(songList)
//    }



}