package com.ssafy.indive.view.songdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.indive.model.response.MusicDetailResponse
import com.ssafy.indive.model.response.ReplyResponse
import com.ssafy.indive.repository.MusicManagerRepository
import com.ssafy.indive.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SongDetailViewModel @Inject constructor(
    private val musicManagerRepository: MusicManagerRepository
) : ViewModel() {


    private val _musicDetails: MutableStateFlow<Result<Response<MusicDetailResponse>>> =
        MutableStateFlow(Result.Unintialized)
    val musicDetails get() = _musicDetails.asStateFlow()

    private var _musicDetail = SingleLiveEvent<MusicDetailResponse>()
    val musicDetail get() = _musicDetail

    private val _musicReplyList: MutableStateFlow<List<ReplyResponse>> = MutableStateFlow(listOf())
    val musicReplyList get() = _musicReplyList.asStateFlow()

    private val _replyListCnt = MutableStateFlow("")
    val replyListCnt get() = _replyListCnt.asStateFlow()

    private val _addReplySuccess = SingleLiveEvent<String>()
    val addReplySuccess get() = _addReplySuccess

    private val _modifySuccess = SingleLiveEvent<String>()
    val modifySuccess get() = _modifySuccess

    private val _deleteSuccess = SingleLiveEvent<String>()
    val deleteSuccess get() = _deleteSuccess

    private val _firstLyrics = SingleLiveEvent<String>()
    val firstLyrics get() = _firstLyrics

    private val _secondLyrics = SingleLiveEvent<String>()
    val secondLyrics get() = _secondLyrics

    fun getMusicDetail(musicSeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.getMusicDetails(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicDetails.value = it
                    _musicDetail.postValue(it.data.body()!!)
                    val lyrics = it.data.body()!!.lyrics
                    setLyrics(lyrics)

                } else if (it is Result.Error) {
                    Log.d(TAG, "Error: ${it.exception}")

                }
            }
        }
    }

    private fun setLyrics(lyrics: String) {

        val arr = lyrics.split("\n")

        var firstText = ""
        var secondText = ""
        if (arr.size > 4) {
            for (i in 0 until 4) {
                firstText += "${arr[i]}\n"
            }

            for (i in 4 until arr.size) {
                secondText += "${arr[i]}\n"
            }
        } else {
            firstText = lyrics
        }

        _firstLyrics.postValue(firstText)
        _secondLyrics.postValue(secondText)

    }

    fun getMusicReply(musicSeq: Long) {
        viewModelScope.launch(Dispatchers.IO) {

            musicManagerRepository.getMusicReply(musicSeq).collectLatest {
                if (it is Result.Success) {
                    _musicReplyList.value = it.data
                } else if (it is Result.Error) {
                    Log.d("SongDetailViewModel_", "getMusicReplyError: ${it.exception}")
                }
            }

        }
    }

    fun addReply(musicSeq: Long, content: String) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.addMusicReply(musicSeq, content).collectLatest {
                if (it is Result.Success) {
                    if (it.data.body()!!) {
                        _musicReplyList.value = listOf()
                        _addReplySuccess.postValue("등록 성공")
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "addReply: $it")
                }
            }

        }
    }

    fun modifyReply(musicSeq: Long, content: String, replySeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.modifyMusicReply(musicSeq, content, replySeq).collectLatest {
                if (it is Result.Success) {
                    if (it.data.body()!!) {
                        _musicReplyList.value = listOf()
                        _modifySuccess.postValue("수정 성공")
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "addReply: $it")
                }
            }

        }
    }

    fun deleteMusicReply(musicSeq: Long, replySeq: Long) {

        viewModelScope.launch(Dispatchers.IO) {
            musicManagerRepository.deleteMusicReply(musicSeq, replySeq).collectLatest {
                if (it is Result.Success) {
                    if (it.data.body()!!) {
                        _musicReplyList.value = listOf()
                        _deleteSuccess.postValue("삭제 성공")
                    }
                } else if (it is Result.Error) {
                    Log.d(TAG, "addReply: $it")
                }
            }


        }
    }
}